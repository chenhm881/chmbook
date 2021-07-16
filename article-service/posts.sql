DROP TABLE IF EXISTS `posts`;
CREATE TABLE `posts` (
  `posts_id` int(11) NOT NULL AUTO_INCREMENT,
  `posts_user_id` int(11) unsigned DEFAULT NULL,
  `posts_title` varchar(255) DEFAULT NULL,
  `posts_content` mediumtext,
  `posts_view_count` int(11) DEFAULT '0',
  `posts_comment_count` int(11) DEFAULT '0',
  `posts_like_count` int(11) DEFAULT '0',
  `posts_is_comment` int(1) unsigned DEFAULT NULL,
  `posts_status` int(1) unsigned DEFAULT '1',
  `posts_order` int(11) unsigned DEFAULT NULL,
  `posts_update_time` datetime DEFAULT NULL,
  `posts_create_time` datetime DEFAULT NULL,
  `posts_summary` text,
  PRIMARY KEY (`posts_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `category_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `category_pid` int(11) DEFAULT NULL,
  `category_name` varchar(50) DEFAULT NULL,
  `category_description` varchar(255) DEFAULT NULL,
  `category_order` int(11) unsigned DEFAULT '1',
  `category_icon` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`category_id`),
  UNIQUE KEY `category_name` (`category_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `comment_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `comment_pid` int(11) unsigned DEFAULT '0',
  `comment_pname` varchar(255) DEFAULT NULL,
  `comment_posts_id` int(11) unsigned DEFAULT NULL,
  `comment_author_name` varchar(50) DEFAULT NULL,
  `comment_author_email` varchar(50) DEFAULT NULL,
  `comment_author_url` varchar(50) DEFAULT NULL,
  `comment_author_avatar` varchar(100) DEFAULT NULL,
  `comment_content` varchar(1000) DEFAULT NULL,
  `comment_agent` varchar(200) DEFAULT NULL,
  `comment_ip` varchar(50) DEFAULT NULL,
  `comment_create_time` datetime DEFAULT NULL,
  `comment_role` int(1) DEFAULT NULL,
  PRIMARY KEY (`comment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS `posts_category`;
CREATE TABLE `posts_category` (
  `posts_id` int(11) DEFAULT NULL,
  `category_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag` (
  `tag_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tag_name` varchar(50) DEFAULT NULL,
  `tag_description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`tag_id`),
  UNIQUE KEY `tag_name` (`tag_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `posts_tag`;
CREATE TABLE `posts_tag` (
  `posts_id` int(11) NOT NULL,
  `tag_id` int(11) NOT NULL,
  PRIMARY KEY (`posts_id`,`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;