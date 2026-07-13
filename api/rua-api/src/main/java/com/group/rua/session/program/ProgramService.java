package com.group.rua.session.program;

import com.group.rua.entities.Module;
import com.group.rua.entities.ProgramSubject;
import com.group.rua.entities.Subject;
import com.group.rua.repositories.ModuleRepo;
import com.group.rua.repositories.ProgramSubjectRepo;
import com.group.rua.repositories.SubjectRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProgramService {

    private final ProgramSubjectRepo programSubjectRepo;
    private final SubjectRepo subjectRepo;
    private final ModuleRepo moduleRepo;

    public ProgramService(ProgramSubjectRepo programSubjectRepo, SubjectRepo subjectRepo, ModuleRepo moduleRepo) {
        this.programSubjectRepo = programSubjectRepo;
        this.subjectRepo = subjectRepo;
        this.moduleRepo = moduleRepo;
    }

    public List<ProgramInfoDTO> getProgramInfo(Integer programId) {

        List<ProgramSubject> programSubjects = programSubjectRepo.findById_ProgramId(programId);

        // si el usuario es nuevo y no tiene asignaturas, devuelve Ok con una lista vacía
        if (programSubjects.isEmpty()) {
            return new ArrayList<>();
        }

        List<Integer> subjectIds = programSubjects.stream()
                .map(ps -> ps.id.subjectId)
                .toList();

        List<Subject> subjects = subjectRepo.findAllById(subjectIds);

        List<ProgramInfoDTO> response = new ArrayList<>();
        for (Subject subject : subjects) {
            List<Module> modules = moduleRepo.findBySubjectId(subject.subjectId);

            response.add(new ProgramInfoDTO(
                    subject.subjectId,
                    subject.subjectName,
                    subject.code,
                    modules
            ));
        }

        return response;
    }

    @org.springframework.transaction.annotation.Transactional
    public void processProgramChanges(Integer programId, List<ProgramChangeDTO> changes) {
        for (ProgramChangeDTO change : changes) {
            if ("Subject".equals(change.field)) {
                handleSubjectChange(programId, change);
            } else if ("Module".equals(change.field)) {
                handleModuleChange(change);
            }
        }
    }

    private void handleSubjectChange(Integer programId, ProgramChangeDTO change) {
        switch (change.action) {
            case "Add":
                Subject newSubject = new Subject();
                newSubject.subjectName = change.newSubjectName;
                newSubject.code = change.code;
                Subject savedSubject = subjectRepo.save(newSubject);

                com.group.rua.entities.ProgramSubjectId psId = new com.group.rua.entities.ProgramSubjectId();
                psId.programId = programId;
                psId.subjectId = savedSubject.subjectId;

                ProgramSubject ps = new ProgramSubject();
                ps.id = psId;
                programSubjectRepo.save(ps);
                break;

            case "Edit":
                subjectRepo.findById(change.subjectId).ifPresent(subject -> {
                    subject.subjectName = change.newName;
                    if (change.code != null) {
                        subject.code = change.code;
                    }
                    subjectRepo.save(subject);
                });
                break;

            case "Remove":
                com.group.rua.entities.ProgramSubjectId deletePsId = new com.group.rua.entities.ProgramSubjectId();
                deletePsId.programId = programId;
                deletePsId.subjectId = change.subjectId;
                programSubjectRepo.deleteById(deletePsId);
                List<Module> modulesToDelete = moduleRepo.findBySubjectId(change.subjectId);
                moduleRepo.deleteAll(modulesToDelete);

                subjectRepo.deleteById(change.subjectId);
                break;
            default:
                throw new IllegalArgumentException("Acción no reconocida: " + change.action);
        }
    }

    private void handleModuleChange(ProgramChangeDTO change) {
        switch (change.action) {
            case "Add":
                Module newModule = new Module();
                newModule.subjectId = change.subjectId;
                newModule.num = change.num;
                moduleRepo.save(newModule);
                break;

            case "Remove":
                moduleRepo.deleteById(change.moduleId);
                break;
            default:
                throw new IllegalArgumentException("Acción no reconocida: " + change.action);
        }
    }
}