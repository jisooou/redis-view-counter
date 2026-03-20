import http from 'k6/http';
import { check } from 'k6';

export const options = {
  vus: 100,          // 동시 사용자 수
  duration: '30s',   // 테스트 시간
};

export default function () {
  const placeId = 4;
  const url = `http://localhost:8080/places/${placeId}/view`;

  const res = http.post(url);

  check(res, {
    'status is 200': (r) => r.status === 200,
  });
}