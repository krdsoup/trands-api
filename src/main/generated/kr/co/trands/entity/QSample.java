package kr.co.trands.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSample is a Querydsl query type for Sample
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSample extends EntityPathBase<Sample> {

    private static final long serialVersionUID = 1659301116L;

    public static final QSample sample = new QSample("sample");

    public final StringPath createdAt = createString("createdAt");

    public final NumberPath<Long> creatorId = createNumber("creatorId", Long.class);

    public final NumberPath<Long> sampleId = createNumber("sampleId", Long.class);

    public final StringPath sampleName = createString("sampleName");

    public final NumberPath<Integer> sampleNumber = createNumber("sampleNumber", Integer.class);

    public final StringPath updatedAt = createString("updatedAt");

    public final NumberPath<Long> updaterId = createNumber("updaterId", Long.class);

    public final StringPath useYn = createString("useYn");

    public QSample(String variable) {
        super(Sample.class, forVariable(variable));
    }

    public QSample(Path<? extends Sample> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSample(PathMetadata metadata) {
        super(Sample.class, metadata);
    }

}

