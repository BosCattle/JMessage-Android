package tech.jiangtao.support.kit.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Class: InvitedAnnotation </br>
 * Description:  </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 18/04/2017 12:48 AM</br>
 * Update: 18/04/2017 12:48 AM </br>
 **/
@Retention(RetentionPolicy.RUNTIME) @Target(ElementType.TYPE) @Inherited @Documented
public @interface InvitedAnnotation {

  Class invitedUri();
}
