package saleson.api.common;

import com.onlinepowers.framework.web.pagination.Pagination;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import saleson.api.common.enumerated.ApiError;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ApiResponseEntity extends ResponseEntity<Map<String, Object>> {

    public ApiResponseEntity(HttpStatus status) {
        super(status);
    }

    public ApiResponseEntity(Map<String, Object> body, HttpStatus status) {
        super(body, status);
    }

    public ApiResponseEntity(MultiValueMap<String, String> headers, HttpStatus status) {
        super(headers, status);
    }

    public ApiResponseEntity(Map<String, Object> body, MultiValueMap<String, String> headers, HttpStatus status) {
        super(body, headers, status);
    }

    public static Builder data() {
        return new Builder();
    }

    public static ResponseEntity<PageImpl> ok(List list, Pagination pagination) {
        PageRequest pageRequest = PageRequest.of(pagination.getCurrentPage() - 1, pagination.getItemsPerPage());
        PageImpl page = new PageImpl(list, pageRequest, pagination.getTotalItems());
        return ResponseEntity.ok(page);
    }

    public static ResponseEntity<Map<String, Object>> error(ApiError error) {
        Builder builder = new Builder();
        builder.status(error.getHttpStatus());
        builder.put("status", error.getHttpStatus().value());
        builder.put("code", error.name());
        builder.put("message", error.getMessage());
        builder.put("description", error.getDescription());
        return builder.error();
    }

    public static ResponseEntity<Map<String, Object>> error(ApiError error, String message) {
        Builder builder = new Builder();
        builder.status(error.getHttpStatus());
        builder.put("status", error.getHttpStatus().value());
        builder.put("code", error.name());
        builder.put("message", message);
        builder.put("description", error.getDescription());
        return builder.error();
    }


    public static class Builder {
        private Map<String, Object> map = new LinkedHashMap<>();
        private HttpStatus status;

        public Builder status(HttpStatus status) {
            this.status = status;
            return this;
        }

        public Builder put(String key, Object value) {
            this.map.put(key, value);
            return this;
        }

        public Builder map(Map<String, Object> map) {
            this.map = map;
            return this;
        }

        public Builder pagination(Pagination pagination) {
            this.map.put("pagination", pagination);
            return this;
        }

        public Builder list(Object list) {
            this.map.put("list", list);
            return this;
        }

        public ResponseEntity<Map<String, Object>> ok() {
            if (map.get("pagination") != null && map.get("list") != null) {
                Pagination pagination = (Pagination) map.get("pagination");
                List list = (List) map.get("list");

                PageRequest pageRequest = PageRequest.of(pagination.getCurrentPage() - 1, pagination.getItemsPerPage());
                PageImpl page = new PageImpl(list, pageRequest, pagination.getTotalItems());

                map.remove("pagination");
                map.remove("list");
                map.put("currentPage", pagination.getCurrentPage());
                map.put("content", page.getContent());
                map.put("last", page.isLast());
                map.put("totalPages", page.getTotalPages());
                map.put("totalElements", page.getTotalElements());
                map.put("size", page.getSize());
                map.put("number", page.getNumber());
                map.put("sort", page.getSort());
                map.put("first", page.isFirst());
                map.put("numberOfElements", page.getNumberOfElements());
            }
            return ResponseEntity.ok(map);
        }

        public ResponseEntity<Map<String, Object>> error() {
            if (this.status == null) {
                status = HttpStatus.BAD_REQUEST;
            }
            return ResponseEntity.status(status).body(map);
        }
    }
}
