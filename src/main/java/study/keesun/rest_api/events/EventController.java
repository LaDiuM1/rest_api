package study.keesun.rest_api.events;

import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Controller
@RequestMapping(value = "/api/events/", produces = MediaTypes.HAL_JSON_VALUE)
public class EventController {

    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event){
        URI createUrl = linkTo(EventController.class).slash("{id}").toUri();
        event.setId(1);
        return ResponseEntity.created(createUrl).body(event);
    }

}
