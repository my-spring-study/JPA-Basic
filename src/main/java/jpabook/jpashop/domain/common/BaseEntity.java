package jpabook.jpashop.domain.common;

import java.time.LocalDateTime;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseEntity {

	private LocalDateTime createdAt;
	private LocalDateTime lastModifiedAt;

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getLastModifiedAt() {
		return lastModifiedAt;
	}

	public void setLastModifiedAt(LocalDateTime lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}
}
