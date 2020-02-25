import { Moment } from 'moment';

export interface ICompany {
  id?: number;
  name?: string;
  businessAreas?: string;
  phone?: string;
  email?: string;
  address?: string;
  createdBy?: string;
  createdDate?: Moment;
  lastModifiedBy?: string;
  lastModifiedDate?: Moment;
  status?: number;
}

export class Company implements ICompany {
  constructor(
    public id?: number,
    public name?: string,
    public businessAreas?: string,
    public phone?: string,
    public email?: string,
    public address?: string,
    public createdBy?: string,
    public createdDate?: Moment,
    public lastModifiedBy?: string,
    public lastModifiedDate?: Moment,
    public status?: number
  ) {}
}
