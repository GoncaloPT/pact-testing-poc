
import {TestBed} from '@angular/core/testing';
import {HttpClientModule} from '@angular/common/http';
import {PersonService} from './person.service';
import {PactWrapper} from '../../../pact-wrapper';

describe('Person Service Pact', () => {
  const provider = new PactWrapper('Provider');
  beforeAll(async () => {
    await provider.init();
  });
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule],
      providers: [PersonService]
    });
  });

  afterEach(async () => {
    await provider.verify();
  });
  afterAll(async () => {
    await provider.finalize();
  });

  describe('list()', () => {
    const expectedResponse = {};

    beforeAll(async () => {
      await provider.addInteraction({
        state: 'people are listed',
        uponReceiving: 'a request to GET all',
        withRequest: {
          method: 'GET',
          path: '/person/'
        },
        willRespondWith: {
          status: 200,
          body: {}
        },
      });
    });

    it('should get an empty list not 404', async () => {
      const personService: PersonService = TestBed.inject(PersonService);
      const response = await personService.list().toPromise();
      await expect(response).toEqual(expectedResponse);
    });
  });


});
