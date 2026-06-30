package dev.sorokin.eventmanager.service;

import dev.sorokin.eventcommon.exception.ResourceNotFoundException;
import dev.sorokin.eventmanager.mapper.EventEntityMapper;
import dev.sorokin.eventmanager.model.domain.Event;
import dev.sorokin.eventmanager.model.entity.RegistrationEntity;
import dev.sorokin.eventmanager.model.enums.EventStatus;
import dev.sorokin.eventmanager.repository.EventRepository;
import dev.sorokin.eventmanager.repository.RegistrationRepository;
import dev.sorokin.eventmanager.repository.UserRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RegistrationEventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final RegistrationRepository registrationRepository;
    private final EventEntityMapper eventEntityMapper;

    public RegistrationEventService(
            EventRepository eventRepository,
            UserRepository userRepository,
            RegistrationRepository registrationRepository,
            EventEntityMapper eventEntityMapper
    ) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.registrationRepository = registrationRepository;
        this.eventEntityMapper = eventEntityMapper;
    }

    @Transactional
    public void registrationEvent(Long eventId, Long currentUserId) {
        var event = eventRepository.findByIdWithLock(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event", eventId));

        if (registrationRepository.existsByUserIdAndEventId(currentUserId, eventId)) {
            throw new IllegalArgumentException("Вы уже зарегистрированы на это мероприятие");
        }

        if (event.getStatus() != EventStatus.WAIT_START) {
            throw new IllegalArgumentException("Нельзя зарегистрироваться на мероприятие со статусами STARTED, CANCELLED и FINISHED");
        }

        if (event.getOccupiedPlaces() >= event.getMaxPlaces()) {
            throw new IllegalArgumentException("Нельзя зарегистрироваться на мероприятие, т.к. свободных мест нет");
        }

        event.setOccupiedPlaces(event.getOccupiedPlaces() + 1);

        registrationRepository.save(
                new RegistrationEntity(
                        event,
                        userRepository.getReferenceById(currentUserId),
                        LocalDateTime.now()
                )
        );
    }

    @Transactional
    public void cancelRegistrationOnEvent(Long eventId, Long currentUserId) {
        var registration = registrationRepository.findByUserIdAndEventId(currentUserId, eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Registration", currentUserId,  eventId));

        var event = registration.getEvent();

        if (event.getStatus() == EventStatus.STARTED || event.getStatus() == EventStatus.FINISHED) {
            throw new IllegalArgumentException("Нельзя отменить регистрацию, если мероприятие уже началось или закончилось");
        }

        registrationRepository.deleteById(registration.getId());
        event.setOccupiedPlaces(event.getOccupiedPlaces() - 1);
    }

    public List<Event> findMyEvents(Long currentUserId, Integer pageNumber, Integer pageSize) {
        Pageable pageable = Pageable
                .ofSize(pageSize)
                .withPage(pageNumber);

        return eventRepository.findEventsByUserId(currentUserId, pageable)
                .stream()
                .map(eventEntityMapper::toDomain)
                .toList();
    }

}