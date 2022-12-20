package com.example.contactmanager.controller.dto;

import java.util.List;

public record CustomPageDto<T>(List<T> content, int page, int size, long totalElements) {
}
