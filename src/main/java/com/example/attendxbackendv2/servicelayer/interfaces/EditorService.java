package com.example.attendxbackendv2.servicelayer.interfaces;


import com.example.attendxbackendv2.presentationlayer.datatransferobjects.EditorDTO;
import com.example.attendxbackendv2.servicelayer.exceptions.EditorAlreadyExistException;
import com.example.attendxbackendv2.servicelayer.exceptions.ResourceNotFoundException;

import java.util.List;

public interface EditorService {

    /**
     * Create an editor.
     * It may throw EditorAlreadyExistException if any editor is already registered using the same email.
     *
     * @param editorDTO The Editor DTO to be created.
     */
    void createEditor(EditorDTO editorDTO) throws EditorAlreadyExistException;

    /**
     * Get all the Editors for UI presentation; it doesn't fetch the details of the editors.
     * It uses pagination and sorting by editor's first name.
     *
     * @param pageNo    page number of the result. See application.properties for the page size.
     * @param ascending sorting order.
     * @return the list of the EditorDTOs requested.
     */
    List<EditorDTO> getAllEditors(int pageNo, boolean ascending);

    /**
     * Fetch editor details by email string.
     *
     * @param email the email of the requested editor.
     * @return the requested EditorDTO if found.
     * @throws ResourceNotFoundException if no such editor is found with the specified email.
     */
    public EditorDTO getEditorByEmail(String email, boolean getDetails) throws ResourceNotFoundException;
    /**
     * Updates the existing Editor by fetching it from the database and applying recent changes.
     *
     * @param editorDTO The DTO containing the editor's email as an identifier and updated fields.
     * @return True if the update was successful.
     * @throws ResourceNotFoundException May be thrown if attempting to access a non-existing editor.
     */
    boolean updateEditor(EditorDTO editorDTO) throws ResourceNotFoundException;

    /**
     * Deletes the editor by its email.
     *
     * @param email the email of the requested editor to be deleted.
     * @return true if the entity is deleted successfully, false otherwise.
     */
    boolean deleteEditor(String email);

}

