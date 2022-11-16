package hust.kien.project.service;

import org.hibernate.Transaction;

public abstract class GeneralLibraryService {
    protected abstract Transaction beginTransaction();
    protected abstract void commitTransaction();
}

