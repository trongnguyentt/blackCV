import { Moment } from 'moment';

export interface ICV {
  id?: number;
  iD?: number;
  name?: string;
  phone?: string;
  email?: string;
  address?: string;
  job?: string;
  avatar?: string;
  fileUploadCV?: string;
  createdBy?: string;
  createdDate?: Moment;
  lastModifiedBy?: string;
}

export class CV implements ICV {
  constructor(
    public id?: number,
    public iD?: number,
    public name?: string,
    public phone?: string,
    public email?: string,
    public address?: string,
    public job?: string,
    public avatar?: string,
    public fileUploadCV?: string,
    public createdBy?: string,
    public createdDate?: Moment,
    public lastModifiedBy?: string
  ) {}
}
