package com.group.rua.session.attendance;

import com.group.rua.entities.Block;
import com.group.rua.entities.Classes;
import com.group.rua.repositories.BlockRepo;
import com.group.rua.repositories.ClassesRepo;
import org.springframework.stereotype.Service;

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
                .orElseThrow(() -> new IllegalArgumentException("La clase especificada no existe."));

        targetClass.isAnulled = true;
        return classesRepo.save(targetClass);
    }

    // Permitir configuración de clase
    public Block configureClassBlockState(Integer blockId, String newStateStr) {
        Block targetBlock = blockRepo.findById(blockId)
                .orElseThrow(() -> new IllegalArgumentException("El bloque especificado no existe."));

        try {
            Block.BlockState newState = Block.BlockState.valueOf(newStateStr.toUpperCase());
            targetBlock.blockState = newState;
            return blockRepo.save(targetBlock);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Estado de bloque inválido. Usa: NORMAL, NO_PROJECTIONS, COMPLETE_ANULED o REMOVED");
        }
    }
}