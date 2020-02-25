import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BlackcvTestModule } from '../../../test.module';
import { CVComponent } from 'app/entities/cv/cv.component';
import { CVService } from 'app/entities/cv/cv.service';
import { CV } from 'app/shared/model/cv.model';

describe('Component Tests', () => {
  describe('CV Management Component', () => {
    let comp: CVComponent;
    let fixture: ComponentFixture<CVComponent>;
    let service: CVService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BlackcvTestModule],
        declarations: [CVComponent],
        providers: []
      })
        .overrideTemplate(CVComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CVComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CVService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CV(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.cVS && comp.cVS[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
