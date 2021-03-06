package asyncresult;

import io.vertx.codetrans.AsyncResultTest;
import io.vertx.codetrans.annotations.CodeTranslate;

import io.vertx.support.HandlerInvoker;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class AsyncResultHandler {

  @CodeTranslate
  public void failed() throws Exception {
    HandlerInvoker.invokeAsyncResultHandlerFailure(res -> {
      AsyncResultTest.setCause(res.cause(), res.failed());
    });
  }

  @CodeTranslate
  public void succeeded() throws Exception {
    HandlerInvoker.invokeAsyncResultHandlerSuccess(res -> {
      AsyncResultTest.setResult(res.result(), res.succeeded());
    });
  }
}
