package com.matchus.domains.tag.domain;

import com.matchus.domains.user.domain.User;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user_tags")
public class UserTag {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(
		name = "tag_id",
		referencedColumnName = "id"
	)
	private Tag tag;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(
		name = "user_id",
		referencedColumnName = "id"
	)
	private User user;

	@Column(nullable = false, columnDefinition = "INT default 1")
	private int tagCount;

	@Builder
	public UserTag(Long id, Tag tag, User user, int tagCount) {
		this.id = id;
		this.tag = tag;
		this.user = user;
		this.tagCount = tagCount;
	}

	public void increaseTagCount() {
		this.tagCount += 1;
	}
}
