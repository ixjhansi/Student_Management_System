package com.sms.response;

import java.util.List;

public class StudentDetailResponse {
    private Long id;
    private String name;
    private String email;
    private String status;

    // Class details
    private Long classId;
    private String className;

    // Laptop history summary
    private List<LaptopHistorySummary> laptopHistories;
    

    public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public Long getClassId() {
		return classId;
	}


	public void setClassId(Long classId) {
		this.classId = classId;
	}


	public String getClassName() {
		return className;
	}


	public void setClassName(String className) {
		this.className = className;
	}


	public List<LaptopHistorySummary> getLaptopHistories() {
		return laptopHistories;
	}


	public void setLaptopHistories(List<LaptopHistorySummary> laptopHistories) {
		this.laptopHistories = laptopHistories;
	}


	public static class LaptopHistorySummary {
        private Long id;
        private String laptopModel;
        private String laptopSerial;
        private String dateIssued;
        private String dateReturned;
        
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getLaptopModel() {
			return laptopModel;
		}
		public void setLaptopModel(String laptopModel) {
			this.laptopModel = laptopModel;
		}
		public String getLaptopSerial() {
			return laptopSerial;
		}
		public void setLaptopSerial(String laptopSerial) {
			this.laptopSerial = laptopSerial;
		}
		public String getDateIssued() {
			return dateIssued;
		}
		public void setDateIssued(String dateIssued) {
			this.dateIssued = dateIssued;
		}
		public String getDateReturned() {
			return dateReturned;
		}
		public void setDateReturned(String dateReturned) {
			this.dateReturned = dateReturned;
		}
        

        // Getters & setters...
    }

    // Getters & setters...
}