import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BlackcvTestModule } from '../../../test.module';
import { ListReasonComponent } from 'app/entities/list-reason/list-reason.component';
import { ListReasonService } from 'app/entities/list-reason/list-reason.service';
import { ListReason } from 'app/shared/model/list-reason.model';

describe('Component Tests', () => {
  describe('ListReason Management Component', () => {
    let comp: ListReasonComponent;
    let fixture: ComponentFixture<ListReasonComponent>;
    let service: ListReasonService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BlackcvTestModule],
        declarations: [ListReasonComponent],
        providers: []
      })
        .overrideTemplate(ListReasonComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ListReasonComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ListReasonService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ListReason(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.listReasons && comp.listReasons[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
