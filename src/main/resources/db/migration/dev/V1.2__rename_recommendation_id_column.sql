-- rename id to recommendation_id
ALTER TABLE recommendation CHANGE COLUMN id recommendation_id BIGINT NOT NULL AUTO_INCREMENT;
