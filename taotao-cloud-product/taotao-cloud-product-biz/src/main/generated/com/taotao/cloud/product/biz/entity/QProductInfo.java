package com.taotao.cloud.product.biz.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QProductInfo is a Querydsl query type for ProductInfo
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QProductInfo extends EntityPathBase<ProductInfo> {

    private static final long serialVersionUID = -746989690L;

    public static final QProductInfo productInfo = new QProductInfo("productInfo");

    public final com.taotao.cloud.data.jpa.entity.QBaseEntity _super = new com.taotao.cloud.data.jpa.entity.QBaseEntity(this);

    //inherited
    public final NumberPath<Long> createBy = _super.createBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createTime = _super.createTime;

    //inherited
    public final BooleanPath delFlag = _super.delFlag;

    public final NumberPath<Long> detailPicId = createNumber("detailPicId", Long.class);

    public final NumberPath<Long> firstPicId = createNumber("firstPicId", Long.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final NumberPath<Long> lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedTime = _super.lastModifiedTime;

    public final StringPath name = createString("name");

    public final NumberPath<Long> picId = createNumber("picId", Long.class);

    public final NumberPath<Long> posterPicId = createNumber("posterPicId", Long.class);

    public final StringPath remark = createString("remark");

    public final NumberPath<Integer> status = createNumber("status", Integer.class);

    public final NumberPath<Long> supplierId = createNumber("supplierId", Long.class);

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public final NumberPath<Long> videoId = createNumber("videoId", Long.class);

    public QProductInfo(String variable) {
        super(ProductInfo.class, forVariable(variable));
    }

    public QProductInfo(Path<? extends ProductInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProductInfo(PathMetadata metadata) {
        super(ProductInfo.class, metadata);
    }

}

