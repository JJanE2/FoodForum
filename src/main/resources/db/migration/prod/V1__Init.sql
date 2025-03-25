CREATE TABLE member (
        member_id bigint not null AUTO_INCREMENT primary key,
        member_name varchar(255),
        member_password varchar(255),
        nickname varchar(255),
        role enum ('ADMIN','CUSTOMER','OWNER'),
        status enum ('ACTIVE','SUSPENDED')
    );

CREATE TABLE menu (
        id bigint not null AUTO_INCREMENT primary key,
        restaurant_id bigint,
        name varchar(255),
        price varchar(255)
    );

CREATE TABLE restaurant (
        restaurant_id bigint not null AUTO_INCREMENT primary key,
        latitude float,
        longitude float,
        total_rating float,
        member_id bigint,
        address varchar(255),
        description varchar(255),
        restaurant_name varchar(255)
    );

CREATE TABLE review (
        review_id bigint not null AUTO_INCREMENT primary key,
        star_rating float,
        date timestamp(6),
        member_id bigint,
        restaurant_id bigint,
        content varchar(255)
    );

-- Relationship setting
alter table menu
       add constraint FK_menu_restaurant
       foreign key (restaurant_id)
       references restaurant (restaurant_id)
       ON DELETE CASCADE;

alter table restaurant
       add constraint FK_restaurant_member
       foreign key (member_id)
       references member (member_id)
       ON DELETE CASCADE;

alter table review
       add constraint FK_review_member
       foreign key (member_id)
       references member (member_id)
       ON DELETE CASCADE;

alter table review
       add constraint FK_review_restaurant
       foreign key (restaurant_id)
       references restaurant (restaurant_id)
       ON DELETE CASCADE;

-- Add Member
INSERT INTO member (member_name, nickname, member_password, role, status) VALUES ('me', 'itsMe', 'raw_password', 'OWNER', 'ACTIVE');
INSERT INTO member (member_name, nickname, member_password, role, status) VALUES ('ad', 'admin', 'raw_password', 'ADMIN', 'ACTIVE');

-- Add Restaurant
insert into restaurant (member_id, restaurant_name, description, address, latitude, longitude, total_rating)
values
    (1, '파파존스', '파파존스는 미국 3대 대표 피자브랜드이며 더 좋은 재료, 더 맛있는 피자를 기업 이념으로 삼고, 고객들에게 가장 맛있고 뛰어난 품질의 피자를 제공하는 것에 최고의 가치를 두고 있습니다.',
     '경기 수원시 영통구 중부대로 238', 37.2750229027839, 127.041864555993, 0.0),
     (1, 'BBQ', '안녕하세요. 고객의 행복을 키우는 BBQ입니다.  BBQ는 Best of the Best Quality (최고 품질 중에서도 최고)를 뜻합니다. BBQ는 그 이름처럼 최고의 원재료만을 사용하여 맛은 물론 고객의 건강까지 생각합니다.  대한민국 대표 치킨 BBQ, 세상에서 가장 맛있고 건강한 치킨을 만들겠습니다.',
     '경기 수원시 영통구 월드컵로97번길 53', 37.2744171241712, 127.050227547913, 0.0),
     (1, '미소야', '매장에 따라 스시, 초밥 메뉴를 판매하지 않을 수 있으니 확인하시고 주문해 주세요.',
     '경기 수원시 팔달구 권광로 373 월드메르디앙아파트주상가동 1층 112호', 37.279167, 127.032423, 0.0),
     (1, '피자스쿨', '포장주문 가능합니다 단체는 미리 예약바랍니다',
     '경기 수원시 영통구 아주로 34', 37.2778493842659, 127.044075357756, 0.0),
     (1, '태화장', '아주대학교 인근 골목에 자리 잡은 한식당입니다. 실내는 특별한 장식 없이 좌식 스타일로 정돈되어 있는데요, 뽀얗게 끓여내는 이 집의 대표 메뉴 돼지국밥을 비롯해 내장국밥, 섞어국밥, 수육백반, 수육 등의 메뉴를 만날 수 있지요.',
     '경기 수원시 팔달구 아주로13번길 22', 37.2761677513435, 127.042658977496, 0.0),
     (1, 'TestRestaurant10', 'Description10', '경기 수원시 팔달구 월드컵로 310', 37.286422902089, 127.035966613143, 0.0),
     (1, 'TestRestaurant11', 'Description11', '경기 수원시 팔달구 월드컵로 310', 37.286422902089, 127.035966613143, 0.0),
     (1, 'TestRestaurant12', 'Description12', '경기 수원시 팔달구 월드컵로 310', 37.286422902089, 127.035966613143, 0.0),
     (1, 'TestRestaurant13', 'Description13', '경기 수원시 팔달구 월드컵로 310', 37.286422902089, 127.035966613143, 0.0),
     (1, 'TestRestaurant14', 'Description14', '경기 수원시 팔달구 월드컵로 310', 37.286422902089, 127.035966613143, 0.0),
     (1, 'TestRestaurant15', 'Description15', '경기 수원시 팔달구 월드컵로 310', 37.286422902089, 127.035966613143, 0.0),
     (1, 'TestRestaurant16', 'Description16', '경기 수원시 팔달구 월드컵로 310', 37.286422902089, 127.035966613143, 0.0),
     (1, 'TestRestaurant17', 'Description17', '경기 수원시 팔달구 월드컵로 310', 37.286422902089, 127.035966613143, 0.0),
     (1, 'TestRestaurant18', 'Description18', '경기 수원시 팔달구 월드컵로 310', 37.286422902089, 127.035966613143, 0.0),
     (1, 'TestRestaurant19', 'Description19', '경기 수원시 팔달구 월드컵로 310', 37.286422902089, 127.035966613143, 0.0),
     (1, 'TestRestaurant20', 'Description20', '경기 수원시 팔달구 월드컵로 310', 37.286422902089, 127.035966613143, 0.0),
     (1, 'TestRestaurant21', 'Description21', '경기 수원시 팔달구 월드컵로 310', 37.286422902089, 127.035966613143, 0.0),
     (1, 'TestRestaurant22', 'Description22', '경기 수원시 팔달구 월드컵로 310', 37.286422902089, 127.035966613143, 0.0),
     (1, 'TestRestaurant23', 'Description23', '경기 수원시 팔달구 월드컵로 310', 37.286422902089, 127.035966613143, 0.0),
     (1, 'TestRestaurant24', 'Description24', '경기 수원시 팔달구 월드컵로 310', 37.286422902089, 127.035966613143, 0.0),
     (1, 'TestRestaurant25', 'Description25', '경기 수원시 팔달구 월드컵로 310', 37.286422902089, 127.035966613143, 0.0),
     (1, 'TestRestaurant26', 'Description26', '경기 수원시 팔달구 월드컵로 310', 37.286422902089, 127.035966613143, 0.0),
     (1, 'TestRestaurant27', 'Description27', '경기 수원시 팔달구 월드컵로 310', 37.286422902089, 127.035966613143, 0.0),
     (1, 'TestRestaurant28', 'Description28', '경기 수원시 팔달구 월드컵로 310', 37.286422902089, 127.035966613143, 0.0),
     (1, 'TestRestaurant29', 'Description29', '경기 수원시 팔달구 월드컵로 310', 37.286422902089, 127.035966613143, 0.0),
     (1, 'TestRestaurant30', 'Description30', '경기 수원시 팔달구 월드컵로 310', 37.286422902089, 127.035966613143, 0.0),
     (1, 'TestRestaurant31', 'Description31', '경기 수원시 팔달구 월드컵로 310', 37.286422902089, 127.035966613143, 0.0),
     (1, 'TestRestaurant32', 'Description32', '경기 수원시 팔달구 월드컵로 310', 37.286422902089, 127.035966613143, 0.0),
     (1, 'TestRestaurant33', 'Description33', '경기 수원시 팔달구 월드컵로 310', 37.286422902089, 127.035966613143, 0.0),
     (1, 'TestRestaurant34', 'Description34', '경기 수원시 팔달구 월드컵로 310', 37.286422902089, 127.035966613143, 0.0),
     (1, 'TestRestaurant35', 'Description35', '경기 수원시 팔달구 월드컵로 310', 37.286422902089, 127.035966613143, 0.0),
     (1, 'TestRestaurant36', 'Description36', '경기 수원시 팔달구 월드컵로 310', 37.286422902089, 127.035966613143, 0.0),
     (1, 'TestRestaurant37', 'Description37', '경기 수원시 팔달구 월드컵로 310', 37.286422902089, 127.035966613143, 0.0),
     (1, 'TestRestaurant38', 'Description38', '경기 수원시 팔달구 월드컵로 310', 37.286422902089, 127.035966613143, 0.0),
     (1, 'TestRestaurant39', 'Description39', '경기 수원시 팔달구 월드컵로 310', 37.286422902089, 127.035966613143, 0.0),
     (1, 'TestRestaurant40', 'Description40', '경기 수원시 팔달구 월드컵로 310', 37.286422902089, 127.035966613143, 0.0),
     (1, 'TestRestaurant41', 'Description41', '경기 수원시 팔달구 월드컵로 310', 37.286422902089, 127.035966613143, 0.0),
     (1, 'TestRestaurant42', 'Description42', '경기 수원시 팔달구 월드컵로 310', 37.286422902089, 127.035966613143, 0.0),
     (1, 'TestRestaurant43', 'Description43', '경기 수원시 팔달구 월드컵로 310', 37.286422902089, 127.035966613143, 0.0),
     (1, 'TestRestaurant44', 'Description44', '경기 수원시 팔달구 월드컵로 310', 37.286422902089, 127.035966613143, 0.0),
     (1, 'TestRestaurant45', 'Description45', '경기 수원시 팔달구 월드컵로 310', 37.286422902089, 127.035966613143, 0.0),
     (1, 'TestRestaurant46', 'Description46', '경기 수원시 팔달구 월드컵로 310', 37.286422902089, 127.035966613143, 0.0),
     (1, 'TestRestaurant47', 'Description47', '경기 수원시 팔달구 월드컵로 310', 37.286422902089, 127.035966613143, 0.0),
     (1, 'TestRestaurant48', 'Description48', '경기 수원시 팔달구 월드컵로 310', 37.286422902089, 127.035966613143, 0.0),
     (1, 'TestRestaurant49', 'Description49', '경기 수원시 팔달구 월드컵로 310', 37.286422902089, 127.035966613143, 0.0),
     (1, 'TestRestaurant50', 'Description50', '경기 수원시 팔달구 월드컵로 310', 37.286422902089, 127.035966613143, 0.0),
     (1, 'TestRestaurant51', 'Description51', '경기 수원시 팔달구 월드컵로 310', 37.286422902089, 127.035966613143, 0.0),
     (1, 'TestRestaurant52', 'Description52', '경기 수원시 팔달구 월드컵로 310', 37.286422902089, 127.035966613143, 0.0),
     (1, 'TestRestaurant53', 'Description53', '경기 수원시 팔달구 월드컵로 310', 37.286422902089, 127.035966613143, 0.0),
     (1, 'TestRestaurant54', 'Description54', '경기 수원시 팔달구 월드컵로 310', 37.286422902089, 127.035966613143, 0.0),
     (1, 'TestRestaurant55', 'Description55', '경기 수원시 팔달구 월드컵로 310', 37.286422902089, 127.035966613143, 0.0),
     (1, 'TestRestaurant56', 'Description56', '경기 수원시 팔달구 월드컵로 310', 37.286422902089, 127.035966613143, 0.0),
     (1, 'TestRestaurant57', 'Description57', '경기 수원시 팔달구 월드컵로 310', 37.286422902089, 127.035966613143, 0.0),
     (1, 'TestRestaurant58', 'Description58', '경기 수원시 팔달구 월드컵로 310', 37.286422902089, 127.035966613143, 0.0),
     (1, 'TestRestaurant59', 'Description59', '경기 수원시 팔달구 월드컵로 310', 37.286422902089, 127.035966613143, 0.0),
     (1, 'TestRestaurant60', 'Description60', '경기 수원시 팔달구 월드컵로 310', 37.286422902089, 127.035966613143, 0.0);


-- Add Review
insert into review (member_id, restaurant_id, content, date, star_rating)
values
    (1, 1, '스파이시 치킨랜치가 맛있어요.', '2025-03-23 12:34:56.123456', 4.0),
    (1, 3, '테스트리뷰1', '2025-03-23 12:34:56.123456', 1.0),
    (1, 3, '테스트리뷰2', '2025-03-23 12:34:56.123456', 1.0),
    (1, 5, '테스트리뷰3', '2025-03-23 12:34:56.123456', 5.0),
    (1, 1, 'TestReview10', '2025-03-23 12:34:56.123456', 4.0),
    (1, 1, 'TestReview11', '2025-03-23 12:34:56.123456', 4.0),
    (1, 1, 'TestReview12', '2025-03-23 12:34:56.123456', 4.0),
    (1, 1, 'TestReview13', '2025-03-23 12:34:56.123456', 4.0),
    (1, 1, 'TestReview14', '2025-03-23 12:34:56.123456', 4.0),
    (1, 1, 'TestReview15', '2025-03-23 12:34:56.123456', 4.0),
    (1, 1, 'TestReview16', '2025-03-23 12:34:56.123456', 4.0),
    (1, 1, 'TestReview17', '2025-03-23 12:34:56.123456', 4.0),
    (1, 1, 'TestReview18', '2025-03-23 12:34:56.123456', 4.0),
    (1, 1, 'TestReview19', '2025-03-23 12:34:56.123456', 4.0),
    (1, 1, 'TestReview20', '2025-03-23 12:34:56.123456', 4.0);

-- Add Menu
insert into menu (name, price, restaurant_id)
values
    ('스파이시 치킨랜치 오리지널(L)', '29500', 1),
    ('존스 페이버릿 오리지널(L)', '29500', 1),
    ('올미트 오리지널(L)', '29500', 1),
    ('아이리쉬 포테이토 오리지널(L)', '27500', 1),
    ('황금올리브치킨', '23000', 2),
    ('카츠모밀 알밥정식', '12000', 3),
    ('고구마피자', '10900', 4),
    ('돼지국밥', '10000', 5),
    ('수육국밥', '17000', 5),
    ('수육', '25000', 5),
    ('내장국밥', '9000', 5),
    ('섞어국밥', '10000', 5);

