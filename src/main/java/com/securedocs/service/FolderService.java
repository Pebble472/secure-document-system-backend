package com.securedocs.service;

import com.securedocs.model.Folder;
import java.util.List;

public interface FolderService {
    Folder createFolder(Folder folder);
    List<Folder> getFolders(String parentId);
    Folder getFolder(String id);
    Folder updateFolder(String id, Folder folder);
    void deleteFolder(String id);
}