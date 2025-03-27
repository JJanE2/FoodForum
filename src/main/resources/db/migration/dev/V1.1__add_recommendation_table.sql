
-- Add Recommendation table
create table recommendation (
    id bigint not null AUTO_INCREMENT primary key,
    review_id bigint,
    member_id bigint,
    constraint FK_recommendation_review foreign key (review_id) references review(review_id) on delete CASCADE,
    constraint FK_recommendation_member foreign key (member_id) references member(member_id) on delete CASCADE
);

-- Add Test Member
 INSERT INTO member (member_name, nickname, member_password, role, status)
 values ('test', 'testMember', '$2a$10$G6.HckmHFYQ1w5MdmGam4OptNi.PpjAqdC/AEKjAWnO8mnbL.0iFG', 'CUSTOMER', 'ACTIVE');