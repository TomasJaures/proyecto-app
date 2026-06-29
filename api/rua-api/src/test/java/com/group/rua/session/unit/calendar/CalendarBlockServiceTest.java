package com.group.rua.session.unit.calendar;

import com.group.rua.entities.Block;
import com.group.rua.entities.Block.BlockState;
import com.group.rua.entities.Block.WeekDay;
import com.group.rua.repositories.BlockRepo;
import com.group.rua.repositories.CalendarBlockRepository;
import com.group.rua.session.calendar.AddBlockDTO;
import com.group.rua.session.calendar.CalendarBlockService;
import com.group.rua.session.calendar.CloneBlockDTO;
import com.group.rua.session.calendar.EditBlockDTO;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        AddBlockDTO dto = new AddBlockDTO(
                10,
                WeekDay.WED,
                LocalTime.of(8, 0),
                LocalTime.of(9, 30)
        );

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

        EditBlockDTO dto = new EditBlockDTO(null, BlockState.NO_PROJECTIONS);

        // Act
        calendarBlockService.editBlock(1, dto);

        // Assert
        assertEquals(BlockState.NO_PROJECTIONS, block.blockState);
        assertEquals(5, block.moduleId);
        verify(blockRepo, times(1)).save(block);
    }

    @Test
    void cloneBlock_ShouldThrowException_WhenOriginalBlockDoesNotExist() {
        // Arrange
        Integer calendarId = 1;
        Integer invalidOriginalBlockId = 999;
        CloneBlockDTO dto = new CloneBlockDTO(Block.WeekDay.TUE, LocalTime.of(8, 30), LocalTime.of(10, 0));

        when(blockRepo.findById(invalidOriginalBlockId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
                calendarBlockService.cloneBlock(calendarId, invalidOriginalBlockId, dto)
        );

        verify(blockRepo, never()).save(any(Block.class));
        verify(calendarBlockRepository, never()).save(any());
    }

    @Test
    void editBlock_ShouldNotAlterEntity_WhenDtoFieldsAreNull() {
        // Arrange
        Integer blockId = 10;
        Block originalBlock = new Block();
        originalBlock.blockId = blockId;
        originalBlock.moduleId = 3;
        originalBlock.blockState = BlockState.NORMAL;

        when(blockRepo.findById(blockId)).thenReturn(Optional.of(originalBlock));

        EditBlockDTO emptyDto = new EditBlockDTO(null, null);

        // Act
        calendarBlockService.editBlock(blockId, emptyDto);

        // Assert
        assertEquals(3, originalBlock.moduleId, "El moduleId no debió cambiar");
        assertEquals(BlockState.NORMAL, originalBlock.blockState, "El blockState no debió cambiar");
        verify(blockRepo, times(1)).save(originalBlock);
    }
}