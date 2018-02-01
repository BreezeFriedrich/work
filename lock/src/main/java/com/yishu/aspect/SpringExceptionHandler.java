package com.yishu.aspect;

import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2018-02-01 12:05 admin
 * @since JDK1.7
 */
@ControllerAdvice
public class SpringExceptionHandler {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(SpringExceptionHandler.class);
//    @ExceptionHandler(value = {Exception.class})
    @ExceptionHandler(RuntimeException.class)
    public String runtimeExceptionHandler(RuntimeException runtimeException,ModelMap modelMap) {
//        LOG.error(runtimeException.getLocalizedMessage());
//        modelMap.put("status", IntegralConstant.FAIL_STATUS);
        LOG.error(null,runtimeException);
        modelMap.put("throwable",runtimeException);
        return "error/error.jsp";
    }

    /*
    @ExceptionHandler(RuntimeException.class)
    public @ResponseBody Map<String,Object> runtimeExceptionHandler(RuntimeException runtimeException) {
        LOG.error(runtimeException.getLocalizedMessage());
        Map model = new TreeMap();
        model.put("status", false);
        return model;
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleOtherExceptions(final Exception ex, final WebRequest req) {
        TResult tResult = new TResult();
        tResult.setStatus(CodeType.V_500);
        tResult.setErrorMessage(ex.getMessage());
        return new ResponseEntity<Object>(tResult, HttpStatus.OK);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void processMethod(MissingServletRequestParameterException ex, HttpServletRequest request , HttpServletResponse response) throws IOException {
        System.out.println("抛异常了！"+ex.getLocalizedMessage());
        LOG.error("抛异常了！"+ex.getLocalizedMessage());
        response.getWriter().printf(ex.getMessage());
        response.flushBuffer();
    }
    */
}
