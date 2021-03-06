package io.vertx.codetrans.expression;

import io.vertx.codegen.type.TypeInfo;
import io.vertx.codetrans.CodeBuilder;
import io.vertx.codetrans.CodeWriter;
import io.vertx.codetrans.MethodSignature;

import java.util.List;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class JsonObjectModel extends ExpressionModel {

  final ExpressionModel expression;

  public JsonObjectModel(CodeBuilder builder, ExpressionModel expression) {
    super(builder);
    this.expression = expression;
  }

  @Override
  public ExpressionModel onMethodInvocation(TypeInfo receiverType, MethodSignature method, TypeInfo returnType, List<ExpressionModel> argumentModels, List<TypeInfo> argumenTypes) {
    String methodName = method.getName();
    switch (methodName) {
      case "put":
        return builder.render(writer -> {
          StringLiteralModel name = (StringLiteralModel) argumentModels.get(0);
          writer.renderJsonObjectAssign(expression, name.value, argumentModels.get(1));
        });
      case "putNull":
        return builder.render(writer -> {
          StringLiteralModel name = (StringLiteralModel) argumentModels.get(0);
          writer.renderJsonObjectAssign(expression, name.value, new NullLiteralModel(builder));
        });
      case "encodePrettily":
      case "encode": {
        return builder.jsonObjectEncoder(expression);
      }
      case "getString":
      case "getJsonObject":
      case "getInteger":
      case "getLong":
      case "getFloat":
      case "getDouble":
      case "getBoolean":
      case "getJsonArray":
      case "getValue":
        if (argumentModels.size() == 1) {
          return builder.render(writer -> {
            StringLiteralModel name = (StringLiteralModel) argumentModels.get(0);
            writer.renderJsonObjectMemberSelect(expression, name.value);
          });
        } else {
          throw unsupported("Invalid arguments " + argumentModels);
        }
      default:
        throw unsupported("Method " + method);
    }
  }
  @Override
  public void render(CodeWriter writer) {
    expression.render(writer);
  }
}
