import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { DocumentsService } from 'app/entities/documents/documents.service';
import { IDocuments, Documents } from 'app/shared/model/documents.model';
import { TypeDoc } from 'app/shared/model/enumerations/type-doc.model';

describe('Service Tests', () => {
  describe('Documents Service', () => {
    let injector: TestBed;
    let service: DocumentsService;
    let httpMock: HttpTestingController;
    let elemDefault: IDocuments;
    let expectedResult: IDocuments | IDocuments[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(DocumentsService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Documents(
        0,
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        TypeDoc.ShipBillOfLading
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            date: currentDate.format(DATE_TIME_FORMAT),
            folderguid: currentDate.format(DATE_TIME_FORMAT),
            docdate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Documents', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            date: currentDate.format(DATE_TIME_FORMAT),
            folderguid: currentDate.format(DATE_TIME_FORMAT),
            docdate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            date: currentDate,
            folderguid: currentDate,
            docdate: currentDate,
          },
          returnedFromService
        );

        service.create(new Documents()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Documents', () => {
        const returnedFromService = Object.assign(
          {
            doctype: 'BBBBBB',
            filename: 'BBBBBB',
            date: currentDate.format(DATE_TIME_FORMAT),
            folderguid: currentDate.format(DATE_TIME_FORMAT),
            folder: 'BBBBBB',
            docnumber: 'BBBBBB',
            docdate: currentDate.format(DATE_TIME_FORMAT),
            description: 'BBBBBB',
            datetime: 'BBBBBB',
            typedoc: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            date: currentDate,
            folderguid: currentDate,
            docdate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Documents', () => {
        const returnedFromService = Object.assign(
          {
            doctype: 'BBBBBB',
            filename: 'BBBBBB',
            date: currentDate.format(DATE_TIME_FORMAT),
            folderguid: currentDate.format(DATE_TIME_FORMAT),
            folder: 'BBBBBB',
            docnumber: 'BBBBBB',
            docdate: currentDate.format(DATE_TIME_FORMAT),
            description: 'BBBBBB',
            datetime: 'BBBBBB',
            typedoc: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            date: currentDate,
            folderguid: currentDate,
            docdate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Documents', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
