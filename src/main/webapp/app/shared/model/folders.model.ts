import { IDocuments } from 'app/shared/model/documents.model';

export interface IFolders {
  id?: number;
  folder?: string;
  documents?: IDocuments[];
}

export class Folders implements IFolders {
  constructor(public id?: number, public folder?: string, public documents?: IDocuments[]) {}
}
