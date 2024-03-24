package com.example.attendxbackendv2.servicelayer.implementations;

import com.example.attendxbackendv2.datalayer.entities.AddressEmbeddable;
import com.example.attendxbackendv2.datalayer.entities.EditorEntity;
import com.example.attendxbackendv2.datalayer.repositories.EditorRepository;
import com.example.attendxbackendv2.presentationlayer.datatransferobjects.AddressDTO;
import com.example.attendxbackendv2.presentationlayer.datatransferobjects.EditorDTO;
import com.example.attendxbackendv2.servicelayer.exceptions.EditorAlreadyExistException;
import com.example.attendxbackendv2.servicelayer.exceptions.ResourceNotFoundException;
import com.example.attendxbackendv2.servicelayer.interfaces.EditorService;
import com.example.attendxbackendv2.servicelayer.mappers.EditorMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EditorServiceImpl implements EditorService {

    @Value("${pagination.size}")
    private int pageSize;

    private final EditorRepository editorRepository;

    public EditorServiceImpl(EditorRepository editorRepository) {
        this.editorRepository = editorRepository;
    }

    @Override
    public void createEditor(EditorDTO editorDTO) throws EditorAlreadyExistException, ResourceNotFoundException {
        // Check if editor already exists with email
        if (editorRepository.findEditorEntitiesByEmailIgnoreCase(editorDTO.getEmail()).isPresent()) {
            throw new EditorAlreadyExistException("Editor already exists with email: " + editorDTO.getEmail());
        }
        EditorEntity editorEntity = EditorMapper.mapEditorDTOToUserBaseEntity(new EditorEntity(), editorDTO, new AddressEmbeddable());
        editorRepository.save(editorEntity);
    }

    @Override
    @Transactional
    public List<EditorDTO> getAllEditors(int pageNo, boolean ascending) {
        Pageable pageable;
        if (ascending) {
            pageable = PageRequest.of(pageNo, pageSize, Sort.by("firstName").ascending());
        } else {
            pageable = PageRequest.of(pageNo, pageSize, Sort.by("firstName").descending());
        }
        List<EditorEntity> userBaseEntities = editorRepository.findAll(pageable).getContent();
        return userBaseEntities.stream().map(userBaseEntity -> EditorMapper.mapUserBaseEntityToEditorDTO(userBaseEntity, new EditorDTO(), new AddressDTO(), false)).toList();
    }

    @Override
    @Transactional
    public EditorDTO getEditorByEmail(String email, boolean getDetails) throws ResourceNotFoundException {
        EditorEntity editor = editorRepository.findEditorEntitiesByEmailIgnoreCase(email)
                .orElseThrow(() -> new ResourceNotFoundException("Editor", "email", email));
        return EditorMapper.mapUserBaseEntityToEditorDTO(editor, new EditorDTO(), new AddressDTO(), getDetails);
    }

    @Override
    @Transactional
    public boolean updateEditor(EditorDTO editorDTO) throws ResourceNotFoundException {
        boolean isUpdated = false;
        EditorEntity editorToUpdate =editorRepository.findEditorEntitiesByEmailIgnoreCase(editorDTO.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Editor",
                        "email",
                        editorDTO.getEmail()));


        // Update other fields on Editor
        EditorMapper.mapEditorDTOToUserBaseEntity(editorToUpdate, editorDTO, new AddressEmbeddable());

        editorRepository.save(editorToUpdate);
        isUpdated = true;
        return isUpdated;
    }

    @Override
    @Transactional
    public boolean deleteEditor(String email) {
        EditorEntity editor = editorRepository.findEditorEntitiesByEmailIgnoreCase(email)
                .orElseThrow(() -> new ResourceNotFoundException("Editor", "email", email));
        editorRepository.delete(editor);
        return true;
    }

    @Override
    public Long getPageCount() {
        return (editorRepository.count() + pageSize - 1) / pageSize;
    }
}
