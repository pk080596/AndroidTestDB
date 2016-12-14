package com.paul.paulk.testdatabase.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by paulk on 12/14/2016.
 */

@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class Employee {
    @Expose @SerializedName ("emp_no") String id;
    @Expose @SerializedName ("birth_date") String birthDate;
    @Expose @SerializedName ("first_name") String firstName;
    @Expose @SerializedName ("last_name") String lastName;
    @Expose @SerializedName ("gender") String gender;
    @Expose @SerializedName ("hire_date") String hireDate;
}
