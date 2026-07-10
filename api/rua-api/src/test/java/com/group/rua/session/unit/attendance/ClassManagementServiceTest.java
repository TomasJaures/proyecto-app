package com.group.rua.session.unit.attendance;

import com.group.rua.entities.Block;
import com.group.rua.entities.Classes;
import com.group.rua.repositories.BlockRepo;
import com.group.rua.repositories.ClassesRepo;
import com.group.rua.session.attendance.ClassManagementService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClassManagementServiceTest {

    @Mock
    private ClassesRepo classesRepo;

    @Mock
    private BlockRepo blockRepo;

    @InjectMocks
    private ClassManagementService classManagementService;

    @Test
    void anullClass_Success() {
        // Arrange
        Classes mockClass = new Classes();
        mockClass.classId = 1;
        mockClass.isAnulled = false;

        when(classesRepo.findById(1)).thenReturn(Optional.of(mockClass));
        when(classesRepo.save(any(Classes.class))).thenReturn(mockClass);

        // Act
        Classes result = classManagementService.anullClass(1);

        // Assert
        assertTrue(result.isAnulled);
        verify(classesRepo, times(1)).save(mockClass);
    }

    @Test
    void anullClass_ThrowsException_WhenNotFound() {
        // Arrange
        when(classesRepo.findById(99)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                classManagementService.anullClass(99));

        assertEquals("La clase especificada no existe.", ex.getMessage());
        verify(classesRepo, never()).save(any());
    }

    @Test
    void configureClassBlockState_Success() {
        // Arrange
        Block mockBlock = new Block();
        mockBlock.blockId = 10;
        mockBlock.blockState = Block.BlockState.NORMAL;

        when(blockRepo.findById(10)).thenReturn(Optional.of(mockBlock));
        when(blockRepo.save(any(Block.class))).thenReturn(mockBlock);

        // Act
        Block result = classManagementService.configureClassBlockState(10, "NO_PROJECTIONS");

        // Assert
        assertEquals(Block.BlockState.NO_PROJECTIONS, result.blockState);
        verify(blockRepo, times(1)).save(mockBlock);
    }

    @Test
    void configureClassBlockState_ThrowsException_WhenNotFound() {
        // Arrange
        when(blockRepo.findById(99)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                classManagementService.configureClassBlockState(99, "NORMAL"));

        assertEquals("El bloque especificado no existe.", ex.getMessage());
        verify(blockRepo, never()).save(any());
    }

    @Test
    void configureClassBlockState_ThrowsException_WhenStateInvalid() {
        // Arrange
        Block mockBlock = new Block();
        mockBlock.blockId = 10;
        when(blockRepo.findById(10)).thenReturn(Optional.of(mockBlock));

        // Act & Assert
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                classManagementService.configureClassBlockState(10, "ESTADO_FALSO"));

        assertTrue(ex.getMessage().contains("Estado de bloque inválido"));
        verify(blockRepo, never()).save(any());
    }
}