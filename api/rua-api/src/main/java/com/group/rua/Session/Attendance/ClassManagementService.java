package com.group.rua.Session.Attendance;

import org.springframework.stereotype.Service;

import com.group.rua.Entities_Classes.Block;
import com.group.rua.Entities_Classes.Classes;
import com.group.rua.Repositories.ClassesRepo;
import com.group.rua.Repositories.BlockRepo;

@Service
public class ClassManagementService {

    private final ClassesRepo classesRepo;
    private final BlockRepo blockRepo;

    public ClassManagementService(ClassesRepo classesRepo, BlockRepo blockRepo) {
        this.classesRepo = classesRepo;
        this.blockRepo = blockRepo;
    }

    // Permitir anulación de clase
    public Classes anullClass(Integer classId) {
        Classes targetClass = classesRepo.findById(classId)
                .orElseThrow(() -> new RuntimeException("La clase especificada no existe."));

        targetClass.isAnulled = true;
        return classesRepo.save(targetClass);
    }

    // Permitir configuración de clase
    public Block configureClassBlockState(Integer blockId, String newStateStr) {
        Block targetBlock = blockRepo.findById(blockId)
                .orElseThrow(() -> new RuntimeException("El bloque especificado no existe."));

        try {
            Block.BlockState newState = Block.BlockState.valueOf(newStateStr.toUpperCase());
            targetBlock.blockState = newState;
            return blockRepo.save(targetBlock);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Estado de bloque inválido. Usa: NORMAL, NO_PROJECTIONS, COMPLETE_ANULED o REMOVED");
        }
    }
}