package com.group.rua.Session.Unit.Calendar;

import com.group.rua.Entities_Classes.Block;
import com.group.rua.Entities_Classes.Block.BlockState;
import com.group.rua.Entities_Classes.Block.WeekDay;
import com.group.rua.Repositories.BlockRepo;
import com.group.rua.Repositories.CalendarBlockRepository;
import com.group.rua.Session.Calendar.AddBlockDTO;
import com.group.rua.Session.Calendar.CalendarBlockService;
import com.group.rua.Session.Calendar.EditBlockDTO;
import com.group.rua.Session.Calendar.MoveBlockDTO;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CalendarBlockServiceTest {

    @Mock
    private CalendarBlockRepository calendarBlockRepository;

    @Mock
    private BlockRepo blockRepo;

    @InjectMocks
    private CalendarBlockService calendarBlockService;

    // Verifica que al remover una clase, su estado cambie a REMOVED sin borrar el registro de la BD.
    @Test
    void removeBlock_ChangesStateToRemoved() {
        // Arrange
        Block block = new Block();
        block.blockId = 1;
        block.blockState = BlockState.NORMAL;

        when(blockRepo.findById(1)).thenReturn(Optional.of(block));

        // Act
        calendarBlockService.removeBlock(1);

        // Assert
        assertEquals(BlockState.REMOVED, block.blockState);
        verify(blockRepo, times(1)).save(block);
    }

    // Asegura que al mover un bloque se actualicen correctamente el día y las horas, guardando los cambios.
    @Test
    void moveBlock_UpdatesDayAndHours() {
        // Arrange
        Block block = new Block();
        block.blockId = 1;
        block.weekDay = WeekDay.MON;

        when(blockRepo.findById(1)).thenReturn(Optional.of(block));

        LocalTime newStart = LocalTime.of(10, 0);
        LocalTime newEnd = LocalTime.of(11, 30);

        // Act
        calendarBlockService.moveBlock(1, WeekDay.TUE, newStart, newEnd);

        // Assert
        assertEquals(WeekDay.TUE, block.weekDay);
        assertEquals(newStart, block.startHour);
        assertEquals(newEnd, block.endHour);
        verify(blockRepo, times(1)).save(block);
    }

    // Comprueba el flujo de añadir una clase: primero crea/guarda el bloque, y luego lo vincula al calendario.
    @Test
    void addBlockToCalendar_SavesBlockAndRelation() {
        // Arrange
        AddBlockDTO dto = new AddBlockDTO();
        dto.moduleId = 10;
        dto.weekDay = WeekDay.WED;
        dto.startHour = LocalTime.of(8, 0);
        dto.endHour = LocalTime.of(9, 30);

        Block savedBlock = new Block();
        savedBlock.blockId = 99; // Simulamos que MySQL le dio la ID 99

        when(blockRepo.save(any(Block.class))).thenReturn(savedBlock);

        // Act
        calendarBlockService.addBlockToCalendar(1, dto);

        // Assert
        verify(blockRepo, times(1)).save(any(Block.class));
        verify(calendarBlockRepository, times(1)).save(any());
    }

    // Valida que la edición sea parcial: solo se actualizan los datos enviados en el DTO, dejando intacto el resto.
    @Test
    void editBlock_UpdatesOnlyProvidedFields() {
        // Arrange
        Block block = new Block();
        block.blockId = 1;
        block.moduleId = 5;
        block.blockState = BlockState.NORMAL;

        when(blockRepo.findById(1)).thenReturn(Optional.of(block));

        EditBlockDTO dto = new EditBlockDTO();
        dto.blockState = BlockState.NO_PROJECTIONS;

        // Act
        calendarBlockService.editBlock(1, dto);

        // Assert
        assertEquals(BlockState.NO_PROJECTIONS, block.blockState);
        assertEquals(5, block.moduleId);
        verify(blockRepo, times(1)).save(block);
    }
}