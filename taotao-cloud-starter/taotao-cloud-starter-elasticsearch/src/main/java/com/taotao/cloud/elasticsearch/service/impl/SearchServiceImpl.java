/*
 * Copyright 2017-2020 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.taotao.cloud.elasticsearch.service.impl;

import com.taotao.cloud.core.model.PageResult;
import com.taotao.cloud.elasticsearch.builder.SearchBuilder;
import com.taotao.cloud.elasticsearch.model.SearchDto;
import com.taotao.cloud.elasticsearch.service.ISearchService;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

import java.io.IOException;

/**
 * 搜索服务实现
 *
 * @author dengtao
 * @date 2020/5/3 07:48
 * @since v1.0
 */
public class SearchServiceImpl implements ISearchService {
    private final ElasticsearchRestTemplate elasticsearchRestTemplate;

    public SearchServiceImpl(ElasticsearchRestTemplate elasticsearchRestTemplate) {
        this.elasticsearchRestTemplate = elasticsearchRestTemplate;
    }

    @Override
    public PageResult<String> strQuery(String indexName, SearchDto searchDto) throws IOException {
        return SearchBuilder.builder(elasticsearchRestTemplate, indexName)
                .setStringQuery(searchDto.getQueryStr())
                .addSort(searchDto.getSortCol(), SortOrder.DESC)
                .setIsHighlight(searchDto.getIsHighlighter())
                .getPage(searchDto.getPage(), searchDto.getLimit());
    }
}
