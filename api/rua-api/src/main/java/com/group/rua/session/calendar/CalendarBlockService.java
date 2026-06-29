package com.group.rua.session.calendar;

import com.group.rua.entities.Block;
import com.group.rua.entities.Block.BlockState;
import com.group.rua.entities.Block.WeekDay;
import com.group.rua.repositories.BlockRepo;
import com.group.rua.repositories.CalendarBlockRepository;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class CalendarBlockService {

    private static final String BLOQUE_NO_ENCONTRADO = "Bloque no encontrado";

    private final CalendarBlockRepository calendarBlockRepository;
    private final BlockRepo blockRepo;

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
                .orElseThrow(() -> new IllegalArgumentException(BLOQUE_NO_ENCONTRADO));

        block.blockState = BlockState.REMOVED;
        blockRepo.save(block);
    }

    // Mover
    public void moveBlock(Integer blockId, WeekDay newDay, LocalTime newStart, LocalTime newEnd) {
        Block block = blockRepo.findById(blockId)
                .orElseThrow(() -> new IllegalArgumentException(BLOQUE_NO_ENCONTRADO));

        block.weekDay = newDay;
        block.startHour = newStart;
        block.endHour = newEnd;

        blockRepo.save(block);
    }

    // Añadir clase
    public void addBlockToCalendar(Integer calendarId, AddBlockDTO dto) {
        Block newBlock = new Block();
        newBlock.moduleId = dto.moduleId();
        newBlock.weekDay = dto.weekDay();
        newBlock.startHour = dto.startHour();
        newBlock.endHour = dto.endHour();
        newBlock.blockState = BlockState.NORMAL;

        newBlock = blockRepo.save(newBlock);

        com.group.rua.entities.CalendarBlockId cbId = new com.group.rua.entities.CalendarBlockId();
        cbId.calendarId = calendarId;
        cbId.blockId = newBlock.blockId;

        com.group.rua.entities.CalendarBlock calendarBlock = new com.group.rua.entities.CalendarBlock();
        calendarBlock.id = cbId;

        calendarBlockRepository.save(calendarBlock);
    }

    // Clonar clase
    public void cloneBlock(Integer calendarId, Integer originalBlockId, CloneBlockDTO dto) {
        Block originalBlock = blockRepo.findById(originalBlockId)
                .orElseThrow(() -> new IllegalArgumentException("Bloque original no encontrado"));

        Block clonedBlock = new Block();
        clonedBlock.moduleId = originalBlock.moduleId;
        clonedBlock.weekDay = dto.weekDay();
        clonedBlock.startHour = dto.startHour();
        clonedBlock.endHour = dto.endHour();
        clonedBlock.blockState = BlockState.NORMAL;

        clonedBlock = blockRepo.save(clonedBlock);

        com.group.rua.entities.CalendarBlockId cbId = new com.group.rua.entities.CalendarBlockId();
        cbId.calendarId = calendarId;
        cbId.blockId = clonedBlock.blockId;

        com.group.rua.entities.CalendarBlock calendarBlock = new com.group.rua.entities.CalendarBlock();
        calendarBlock.id = cbId;

        calendarBlockRepository.save(calendarBlock);
    }

    // Editar clase
    public void editBlock(Integer blockId, EditBlockDTO dto) {
        Block block = blockRepo.findById(blockId)
                .orElseThrow(() -> new IllegalArgumentException(BLOQUE_NO_ENCONTRADO));

        if (dto.moduleId() != null) {
            block.moduleId = dto.moduleId();
        }
        if (dto.blockState() != null) {
            block.blockState = dto.blockState();
        }

        blockRepo.save(block);
    }
}