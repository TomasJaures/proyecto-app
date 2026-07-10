package com.group.rua.session.unit.calendar;

import com.group.rua.session.calendar.CalendarService;
import com.group.rua.session.calendar.ScheduleChangeDTO;
import com.group.rua.entities.Block;
import com.group.rua.entities.CalendarBlock;
import com.group.rua.repositories.BlockRepo;
import com.group.rua.repositories.CalendarBlockRepository;
import com.group.rua.repositories.ClassesRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CalendarServiceTest {

    @Mock
    private ClassesRepo classesRepo;

    @Mock
    private BlockRepo blockRepo;

    @Mock
    private CalendarBlockRepository calendarBlockRepo;

    @InjectMocks
    private CalendarService calendarService;

    private List<ScheduleChangeDTO> changes;

    @BeforeEach
    void setUp() {
        changes = new ArrayList<>();
    }

    @Test
    void processScheduleChanges_AddAction_CreatesNewBlock() {
        // Arrange
        ScheduleChangeDTO change = new ScheduleChangeDTO();
        change.setAction("Add");
        change.setDay("MON");
        change.setStartHour("08:30");
        change.setEndHour("09:40");
        change.setModuleId(5);
        changes.add(change);

        Block mockSavedBlock = new Block();
        mockSavedBlock.blockId = 99;
        when(blockRepo.save(any(Block.class))).thenReturn(mockSavedBlock);

        // Act
        calendarService.processScheduleChanges(1, changes);

        verify(blockRepo, times(1)).save(any(Block.class));
        verify(calendarBlockRepo, times(1)).save(any(CalendarBlock.class));
    }

    @Test
    void processScheduleChanges_MoveAction_UpdatesExistingBlock() {
        // Arrange
        ScheduleChangeDTO change = new ScheduleChangeDTO();
        change.setAction("Move");
        change.setBlockId(10); // Bloque que ya existe
        change.setDay("WED");
        change.setStartHour("14:30");
        change.setEndHour("15:40");
        change.setModuleId(5);
        changes.add(change);

        Block existingBlock = new Block();
        existingBlock.blockId = 10;
        when(blockRepo.findById(10)).thenReturn(Optional.of(existingBlock));

        // Act
        calendarService.processScheduleChanges(1, changes);

        // Assert
        verify(blockRepo, times(1)).findById(10);
        verify(blockRepo, times(1)).save(existingBlock);
        assertEquals(Block.WeekDay.WED, existingBlock.weekDay);
        assertEquals(LocalTime.parse("14:30"), existingBlock.startHour);
    }

    @Test
    void processScheduleChanges_RemoveAction_DeletesBlock() {
        // Arrange
        ScheduleChangeDTO change = new ScheduleChangeDTO();
        change.setAction("Remove");
        change.setBlockId(12);
        changes.add(change);

        // Act
        calendarService.processScheduleChanges(1, changes);

        // Assert
        verify(calendarBlockRepo, times(1)).deleteById(any());
        verify(blockRepo, times(1)).deleteById(12);
    }

    @Test
    void processScheduleChanges_InvalidAction_ThrowsException() {
        // Arrange
        ScheduleChangeDTO change = new ScheduleChangeDTO();
        change.setAction("AccionInventada");
        changes.add(change);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            calendarService.processScheduleChanges(1, changes);
        });

        assertEquals("Acción no reconocida: AccionInventada", exception.getMessage());
        verify(blockRepo, never()).save(any());
    }

    @Test
    void generateClassesForCurrentWeek_CreatesOnlyNewClasses() {
        // Arrange
        Integer calendarId = 1;
        Block block = new Block();
        block.blockId = 50;
        block.weekDay = Block.WeekDay.MON;
        List<Block> blocks = new ArrayList<>();
        blocks.add(block);

        when(blockRepo.findBlocksByCalendarId(calendarId)).thenReturn(blocks);
        when(classesRepo.existsByBlockIdAndClassDate(eq(50), any())).thenReturn(false);
        when(classesRepo.findAllClassesByCalendarIdAndWeek(eq(calendarId), any(), any())).thenReturn(new ArrayList<>());

        // Act
        calendarService.generateClassesForCurrentWeek(calendarId);

        // Assert
        verify(blockRepo, times(1)).findBlocksByCalendarId(calendarId);
        verify(classesRepo, times(1)).existsByBlockIdAndClassDate(eq(50), any());
        verify(classesRepo, times(1)).saveAll(anyList());
    }
}