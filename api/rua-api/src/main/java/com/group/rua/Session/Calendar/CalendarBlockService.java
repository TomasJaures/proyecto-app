package com.group.rua.Session.Calendar;

import com.group.rua.Entities_Classes.Block;
import com.group.rua.Entities_Classes.Block.BlockState;
import com.group.rua.Entities_Classes.Block.WeekDay;
import com.group.rua.Repositories.BlockRepo;
import com.group.rua.Repositories.CalendarBlockRepository;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class CalendarBlockService {
    /**
     * Obtiene todos los bloques de un calendario dado su ID.
     *
     * Si el calendario no existe o no tiene bloques asignados,
     * devuelve una lista vacía (el frontend maneja el caso vacío).
     */
    private final CalendarBlockRepository calendarBlockRepository;
    private final BlockRepo blockRepo; // Añadimos el repositorio de bloques

    public CalendarBlockService(CalendarBlockRepository calendarBlockRepository, BlockRepo blockRepo) {
        this.calendarBlockRepository = calendarBlockRepository;
        this.blockRepo = blockRepo;
    }

    public List<CalendarBlockDTO> getBlocksByCalendar(Integer calendarId) {
        return calendarBlockRepository.findBlocksByCalendarId(calendarId);
    }

    // Remover
    public void removeBlock(Integer blockId) {
        Block block = blockRepo.findById(blockId)
                .orElseThrow(() -> new RuntimeException("Bloque no encontrado"));

        block.blockState = BlockState.REMOVED; // Cambiamos el estado (Soft Delete)
        blockRepo.save(block);
    }

    // Mover
    public void moveBlock(Integer blockId, WeekDay newDay, LocalTime newStart, LocalTime newEnd) {
        Block block = blockRepo.findById(blockId)
                .orElseThrow(() -> new RuntimeException("Bloque no encontrado"));

        block.weekDay = newDay;
        block.startHour = newStart;
        block.endHour = newEnd;

        blockRepo.save(block);
    }

    // Añadir clase
    public void addBlockToCalendar(Integer calendarId, AddBlockDTO dto) {
        Block newBlock = new Block();
        newBlock.moduleId = dto.moduleId;
        newBlock.weekDay = dto.weekDay;
        newBlock.startHour = dto.startHour;
        newBlock.endHour = dto.endHour;
        newBlock.blockState = BlockState.NORMAL; // Estado por defecto

        newBlock = blockRepo.save(newBlock);

        // Vincular el bloque recién creado al calendario
        com.group.rua.Entities_Classes.CalendarBlockId cbId = new com.group.rua.Entities_Classes.CalendarBlockId();
        cbId.calendarId = calendarId;
        cbId.blockId = newBlock.blockId;

        com.group.rua.Entities_Classes.CalendarBlock calendarBlock = new com.group.rua.Entities_Classes.CalendarBlock();
        calendarBlock.id = cbId;

        calendarBlockRepository.save(calendarBlock);
    }

    // Clonar clase
    public void cloneBlock(Integer calendarId, Integer originalBlockId, CloneBlockDTO dto) {
        //Buscar el bloque original para copiar sus datos (como la asignatura/módulo)
        Block originalBlock = blockRepo.findById(originalBlockId)
                .orElseThrow(() -> new RuntimeException("Bloque original no encontrado"));

        // Crear el clon con el mismo módulo, pero nuevas horas
        Block clonedBlock = new Block();
        clonedBlock.moduleId = originalBlock.moduleId; // aqui está lo principal de la clonación
        clonedBlock.weekDay = dto.weekDay;
        clonedBlock.startHour = dto.startHour;
        clonedBlock.endHour = dto.endHour;
        clonedBlock.blockState = BlockState.NORMAL;

        // Guardar el clon para generar su ID
        clonedBlock = blockRepo.save(clonedBlock);

        // Vincular el bloque clonado al calendario
        com.group.rua.Entities_Classes.CalendarBlockId cbId = new com.group.rua.Entities_Classes.CalendarBlockId();
        cbId.calendarId = calendarId;
        cbId.blockId = clonedBlock.blockId;

        com.group.rua.Entities_Classes.CalendarBlock calendarBlock = new com.group.rua.Entities_Classes.CalendarBlock();
        calendarBlock.id = cbId;

        calendarBlockRepository.save(calendarBlock);
    }

    // Editar clase
    public void editBlock(Integer blockId, EditBlockDTO dto) {
        Block block = blockRepo.findById(blockId)
                .orElseThrow(() -> new RuntimeException("Bloque no encontrado"));

        // Actualizamos solo si nos envían un dato nuevo
        if (dto.moduleId != null) {
            block.moduleId = dto.moduleId;
        }
        if (dto.blockState != null) {
            block.blockState = dto.blockState;
        }

        blockRepo.save(block);
    }
}
