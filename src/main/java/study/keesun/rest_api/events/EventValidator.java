package study.keesun.rest_api.events;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.time.LocalDateTime;

@Component
public class EventValidator {
    public void validate(EventDto eventDto, Errors errors) {
        if (eventDto.getBasePrice() > eventDto.getMaxPrice() && eventDto.getMaxPrice() != 0) {
//            errors.rejectValue("basePrice", "wrongValue", "BasePrice is Wrong");
//            errors.rejectValue("maxPrice", "wrongValue", "maxPrice is Wrong");
            errors.reject("wrongPrices", "Values for Prices are Wrong");
        }

        LocalDateTime beginEventDateTime = eventDto.getBeginEventDateTime();
        LocalDateTime endEventDateTime = eventDto.getEndEventDateTime();
        LocalDateTime beginEnrollmentDateTime = eventDto.getBeginEnrollmentDateTime();
        LocalDateTime closeEnrollmentDateTime = eventDto.getCloseEnrollmentDateTime();
        if(beginEventDateTime !=null && endEventDateTime != null && beginEnrollmentDateTime != null && closeEnrollmentDateTime != null &&
                (endEventDateTime.isBefore(eventDto.getBeginEventDateTime()) ||
                endEventDateTime.isBefore(eventDto.getCloseEnrollmentDateTime()) ||
                endEventDateTime.isBefore(eventDto.getBeginEnrollmentDateTime()))){
            errors.rejectValue("endEventDateTime", "wrongValue", "endEventDateTime is Wrong" );
        }

        // TODO beginEventDateTime
        // TODO closeEnrollmentDateTime
    }
}
