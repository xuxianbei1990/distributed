package concurrent.program.application;

/**  责任链
 * User: xuxb
 * Date: 2018-09-11
 * Time: 17:17
 * Version:V1.0
 */
public interface RequestProcessor {
    void processorRequest(Request request);
}
