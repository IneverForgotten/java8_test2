package org.nfec.common.constraint;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Calendar;
import java.util.Date;

/**
 * 自定义注解实现
 */
@Slf4j
public class TimeStampValidConstraintValidator implements ConstraintValidator<TimeStampValid, Object> {

    @Value("${timestampValid.check}")
    private boolean check;

    @Value("${timestampValid.before}")
    private int before;

    @Value("${timestampValid.after}")
    private int after;

    /**
     * 校验时间戳是否正确
     * @param o
     * @param constraintValidatorContext
     * @return
     */
    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {

        try {
            //不检查，直接返回true
            if (!check)
                return true;

            //支持long、Date、Calendar
            Calendar calendar = Calendar.getInstance();

            if (o instanceof Long)
                calendar.setTimeInMillis(((Long) o).longValue());
            else if (o instanceof Date)
                calendar.setTime((Date)o);
            else if (o instanceof Calendar)
                calendar = (Calendar)o;
            else
                throw new Exception("数据类型错误");

            Calendar past = Calendar.getInstance();
            past.add(Calendar.SECOND, (-1) * before);

            Calendar future = Calendar.getInstance();
            future.add(Calendar.SECOND, after);

            if (calendar.after(past) && calendar.before(future))
                return true;

        } catch (Exception e) {
            e.printStackTrace();

            log.error(e.getMessage());
        }


        return false;
    }

    @Override
    public void initialize(TimeStampValid constraintAnnotation) {
    }
}
