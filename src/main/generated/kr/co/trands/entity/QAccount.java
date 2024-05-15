package kr.co.trands.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAccount is a Querydsl query type for Account
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAccount extends EntityPathBase<Account> {

    private static final long serialVersionUID = 1151532315L;

    public static final QAccount account = new QAccount("account");

    public final NumberPath<Long> accountId = createNumber("accountId", Long.class);

    public final StringPath authCode = createString("authCode");

    public final StringPath createdAt = createString("createdAt");

    public final NumberPath<Long> creatorId = createNumber("creatorId", Long.class);

    public final StringPath email = createString("email");

    public final NumberPath<Integer> loginFailCnt = createNumber("loginFailCnt", Integer.class);

    public final StringPath name = createString("name");

    public final StringPath passwd = createString("passwd");

    public final StringPath passwordModifyDt = createString("passwordModifyDt");

    public final StringPath phone = createString("phone");

    public final StringPath profileUrl = createString("profileUrl");

    public final StringPath statusCode = createString("statusCode");

    public final StringPath tempPasswordYn = createString("tempPasswordYn");

    public final StringPath termAgreeDt = createString("termAgreeDt");

    public final StringPath termAgreeYn = createString("termAgreeYn");

    public final StringPath updatedAt = createString("updatedAt");

    public final NumberPath<Long> updaterId = createNumber("updaterId", Long.class);

    public final StringPath useYn = createString("useYn");

    public QAccount(String variable) {
        super(Account.class, forVariable(variable));
    }

    public QAccount(Path<? extends Account> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAccount(PathMetadata metadata) {
        super(Account.class, metadata);
    }

}

