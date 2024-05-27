package study.keesun.rest_api.events;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Controller
@RequestMapping(value = "/api/events/", produces = MediaTypes.HAL_JSON_VALUE)
public class EventController {
    private final EventRepository eventRepository;
    private final ModelMapper modelMapper;
    private final EventValidator eventValidator;

    public EventController(EventRepository eventRepository, ModelMapper modelMapper, EventValidator eventValidator) {
        this.eventRepository = eventRepository;
        this.modelMapper = modelMapper;
        this.eventValidator = eventValidator;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEvent(@PathVariable Integer id){
        Event event = eventRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        return ResponseEntity.ok(event);
    }

    @PostMapping
    public ResponseEntity<?> createEvent(@RequestBody @Valid EventDto eventDto, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.badRequest().body(errors);
        }
        eventValidator.validate(eventDto, errors);
        Event event = modelMapper.map(eventDto, Event.class);
        event.update();
        if(errors.hasErrors()){
            return ResponseEntity.badRequest().body(errors);
        }
        Event newEvent = this.eventRepository.save(event);

        WebMvcLinkBuilder createdLink = linkTo(methodOn(EventController.class).getEvent(event.getId()));

        EntityModel<Event> eventModel = EntityModel.of(newEvent);
        eventModel.add(createdLink.withSelfRel());
        eventModel.add(linkTo(methodOn(EventController.class).getEvent(event.getId())).withRel("query-events"));
        eventModel.add(linkTo(methodOn(EventController.class).getEvent(event.getId())).withRel("update-events"));

        return ResponseEntity.created(createdLink.toUri()).body(eventModel);
    }

}
