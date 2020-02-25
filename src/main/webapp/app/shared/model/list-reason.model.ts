import { Moment } from 'moment';

export interface IListReason {
  id?: number;
  document?: string;
  reason?: string;
  createdBy?: string;
  createdDate?: Moment;
  lastModifiedBy?: string;
  lastModifiedDate?: Moment;
  status?: number;
}

export class ListReason implements IListReason {
  constructor(
    public id?: number,
    public document?: string,
    public reason?: string,
    public createdBy?: string,
    public createdDate?: Moment,
    public lastModifiedBy?: string,
    public lastModifiedDate?: Moment,
    public status?: number
  ) {}
}
