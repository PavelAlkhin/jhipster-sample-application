import { Moment } from 'moment';
import { IFolders } from 'app/shared/model/folders.model';
import { TypeDoc } from 'app/shared/model/enumerations/type-doc.model';

export interface IDocuments {
  id?: number;
  doctype?: string;
  filename?: string;
  date?: Moment;
  folderguid?: Moment;
  folder?: string;
  docnumber?: string;
  docdate?: Moment;
  description?: string;
  datetime?: string;
  typedoc?: TypeDoc;
  folderid?: IFolders;
}

export class Documents implements IDocuments {
  constructor(
    public id?: number,
    public doctype?: string,
    public filename?: string,
    public date?: Moment,
    public folderguid?: Moment,
    public folder?: string,
    public docnumber?: string,
    public docdate?: Moment,
    public description?: string,
    public datetime?: string,
    public typedoc?: TypeDoc,
    public folderid?: IFolders
  ) {}
}
