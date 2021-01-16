package eco.vessel.doc.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

import eco.vessel.doc.domain.enumeration.TypeDoc;

/**
 * The Documents entity.
 */
@ApiModel(description = "The Documents entity.")
@Entity
@Table(name = "documents")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Documents implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * The firstname attribute.
     */
    @ApiModelProperty(value = "The firstname attribute.")
    @Column(name = "doctype")
    private String doctype;

    @Column(name = "filename")
    private String filename;

    @Column(name = "date")
    private Instant date;

    @Column(name = "folderguid")
    private Instant folderguid;

    @Column(name = "folder")
    private String folder;

    @Column(name = "docnumber")
    private String docnumber;

    @Column(name = "docdate")
    private Instant docdate;

    @Column(name = "description")
    private String description;

    @Column(name = "datetime")
    private String datetime;

    @Enumerated(EnumType.STRING)
    @Column(name = "typedoc")
    private TypeDoc typedoc;

    @ManyToOne
    @JsonIgnoreProperties(value = "documents", allowSetters = true)
    private Folders folderid;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDoctype() {
        return doctype;
    }

    public Documents doctype(String doctype) {
        this.doctype = doctype;
        return this;
    }

    public void setDoctype(String doctype) {
        this.doctype = doctype;
    }

    public String getFilename() {
        return filename;
    }

    public Documents filename(String filename) {
        this.filename = filename;
        return this;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Instant getDate() {
        return date;
    }

    public Documents date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Instant getFolderguid() {
        return folderguid;
    }

    public Documents folderguid(Instant folderguid) {
        this.folderguid = folderguid;
        return this;
    }

    public void setFolderguid(Instant folderguid) {
        this.folderguid = folderguid;
    }

    public String getFolder() {
        return folder;
    }

    public Documents folder(String folder) {
        this.folder = folder;
        return this;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getDocnumber() {
        return docnumber;
    }

    public Documents docnumber(String docnumber) {
        this.docnumber = docnumber;
        return this;
    }

    public void setDocnumber(String docnumber) {
        this.docnumber = docnumber;
    }

    public Instant getDocdate() {
        return docdate;
    }

    public Documents docdate(Instant docdate) {
        this.docdate = docdate;
        return this;
    }

    public void setDocdate(Instant docdate) {
        this.docdate = docdate;
    }

    public String getDescription() {
        return description;
    }

    public Documents description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDatetime() {
        return datetime;
    }

    public Documents datetime(String datetime) {
        this.datetime = datetime;
        return this;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public TypeDoc getTypedoc() {
        return typedoc;
    }

    public Documents typedoc(TypeDoc typedoc) {
        this.typedoc = typedoc;
        return this;
    }

    public void setTypedoc(TypeDoc typedoc) {
        this.typedoc = typedoc;
    }

    public Folders getFolderid() {
        return folderid;
    }

    public Documents folderid(Folders folders) {
        this.folderid = folders;
        return this;
    }

    public void setFolderid(Folders folders) {
        this.folderid = folders;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Documents)) {
            return false;
        }
        return id != null && id.equals(((Documents) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Documents{" +
            "id=" + getId() +
            ", doctype='" + getDoctype() + "'" +
            ", filename='" + getFilename() + "'" +
            ", date='" + getDate() + "'" +
            ", folderguid='" + getFolderguid() + "'" +
            ", folder='" + getFolder() + "'" +
            ", docnumber='" + getDocnumber() + "'" +
            ", docdate='" + getDocdate() + "'" +
            ", description='" + getDescription() + "'" +
            ", datetime='" + getDatetime() + "'" +
            ", typedoc='" + getTypedoc() + "'" +
            "}";
    }
}
