package eco.vessel.doc.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Folders.
 */
@Entity
@Table(name = "folders")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Folders implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "folder")
    private String folder;

    @OneToMany(mappedBy = "folderid")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Documents> documents = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFolder() {
        return folder;
    }

    public Folders folder(String folder) {
        this.folder = folder;
        return this;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public Set<Documents> getDocuments() {
        return documents;
    }

    public Folders documents(Set<Documents> documents) {
        this.documents = documents;
        return this;
    }

    public Folders addDocuments(Documents documents) {
        this.documents.add(documents);
        documents.setFolderid(this);
        return this;
    }

    public Folders removeDocuments(Documents documents) {
        this.documents.remove(documents);
        documents.setFolderid(null);
        return this;
    }

    public void setDocuments(Set<Documents> documents) {
        this.documents = documents;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Folders)) {
            return false;
        }
        return id != null && id.equals(((Folders) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Folders{" +
            "id=" + getId() +
            ", folder='" + getFolder() + "'" +
            "}";
    }
}
