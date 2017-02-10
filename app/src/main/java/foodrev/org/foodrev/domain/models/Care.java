package foodrev.org.foodrev.domain.models;


import com.google.firebase.database.DatabaseReference;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Care {
    private Integer mCareID = null;
    private CareStatus mCareStatus = null;
    private String mCareTitle = null;
    private Integer mCoordinatorID = 0;
    private String mDate = null;
    private String mDriverID = null;
    private Integer mFoodValue = null;
    private String mFoodDescription = null;
    private ArrayList<Step> mSteps = null;

    public Care() {
    }

    public Care(Care otherCare) {
        this.deepCopy(otherCare);
    }

    public static String[] GetCareStatusStringArray() {
        return new String[]{
                "PENDING", "ASSIGNED", "STARTED", "FINISHED"
        };
    }

    public static boolean isValidCareStatusString(String s) {
        switch (s) {
            case "PENDING":
                return true;
            case "ACCEPTED":
                return true;
            case "ASSIGNED":
                return true;
            case "STARTED":
                return true;
            case "FINISHED":
                return true;
            default:
                return false;
        }
    }

    public static CareStatus CareStatusStringToCareStatus(String s) {
        switch (s) {
            case "PENDING":
                return CareStatus.PENDING;
            case "ACCEPTED":
                return CareStatus.ACCEPTED;
            case "ASSIGNED":
                return CareStatus.ASSIGNED;
            case "STARTED":
                return CareStatus.STARTED;
            case "FINISHED":
                return CareStatus.FINISHED;
            default:
                throw new RuntimeException("Invalid conversion");
        }
    }

    public static String CareStatusToCareStatusString(CareStatus c) {
        switch (c) {
            case PENDING:
                return "PENDING";
            case ACCEPTED:
                return "ACCEPTED";
            case ASSIGNED:
                return "ASSIGNED";
            case STARTED:
                return "STARTED";
            case FINISHED:
                return "FINISHED";
            default:
                throw new RuntimeException("Invalid");
        }
    }

    public static String[] GetStepStatusStringArray() {
        return new String[]{
                "PENDING", "STARTED", "FINISHED"
        };
    }

    public static boolean isValidStepStatusString(String s) {
        switch (s) {
            case "PENDING":
                return true;
            case "STARTED":
                return true;
            case "FINISHED":
                return true;
            default:
                return false;
        }
    }

    public static StepStatus StepStatusStringToStepStatus(String s) {
        switch (s) {
            case "PENDING":
                return StepStatus.PENDING;
            case "STARTED":
                return StepStatus.STARTED;
            case "FINISHED":
                return StepStatus.FINISHED;
            default:
                throw new RuntimeException("Please call isValidStepStatusString(" + s + ")");
        }
    }

    public static String StepStatusToStepStatusString(StepStatus s) {
        switch (s) {
            case PENDING:
                return "PENDING";
            case STARTED:
                return "STARTED";
            case FINISHED:
                return "FINISHED";
            default:
                throw new RuntimeException("Invalid conversion");
        }
    }

    public static String[] GetStepTypeStringArray() {
        return new String[]{
                "DRIVE_DONATION_CENTER", "DRIVE_COMMUNITY_CENTER"
        };
    }

    public static boolean isValidStepTypeString(String s) {
        switch (s) {
            case "DRIVE_DONATION_CENTER":
                return true;
            case "DRIVE_COMMUNITY_CENTER":
                return true;
            default:
                return false;
        }
    }

    public static StepType StepTypeStringToStepType(String s) {
        switch (s) {
            case "DRIVE_DONATION_CENTER":
                return StepType.DRIVE_DONATION_CENTER;
            case "DRIVE_COMMUNITY_CENTER":
                return StepType.DRIVE_COMMUNITY_CENTER;
            default:
                throw new RuntimeException("Please call isValidStepStatusString(" + s + ")");
        }
    }

    public static String StepTypeToStepTypeString(StepType s) {
        switch (s) {
            case DRIVE_DONATION_CENTER:
                return "DRIVE_DONATION_CENTER";
            case DRIVE_COMMUNITY_CENTER:
                return "DRIVE_COMMUNITY_CENTER";
            default:
                throw new RuntimeException("Invalid conversion");
        }
    }

    public Integer getCareID() {
        return mCareID;
    }

    public void setCareID(Integer careID) {
        mCareID = careID;
    }

    public CareStatus getCareStatus() {
        return mCareStatus;
    }

    public void setCareStatus(CareStatus careStatus) {
        mCareStatus = careStatus;
    }

    public String getCareTitle() {
        return mCareTitle;
    }

    public void setCareTitle(String careTitle) {
        mCareTitle = careTitle;
    }

    public Integer getCoordinatorID() {
        return mCoordinatorID;
    }

    public void setCoordinatorID(Integer coordinatorID) {
        mCoordinatorID = coordinatorID;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getDriverID() {
        return mDriverID;
    }

    public void setDriverID(String driverID) {
        mDriverID = driverID;
    }

    public Integer getFoodValue() {
        return mFoodValue;
    }

    public String getFoodDescription() {
        return mFoodDescription;
    }

    public void setFoodValue(Integer foodValue) {
        mFoodValue = foodValue;
    }

    public void setFoodDescription(String description) {
        mFoodDescription = description;
    }

    public ArrayList<Step> getSteps() {
        return mSteps;
    }

    public void setSteps(ArrayList<Step> steps) {
        mSteps = steps;
    }

    private void deepCopy(Care otherCare) {
        this.mCareID = otherCare.mCareID;
        this.mCareStatus = otherCare.mCareStatus;
        this.mCareTitle = otherCare.mCareTitle;
        this.mCoordinatorID = otherCare.mCoordinatorID;
        this.mDate = otherCare.mDate;
        this.mDriverID = otherCare.mDriverID;
        this.mFoodValue = otherCare.mFoodValue;
        this.mFoodDescription = otherCare.mFoodDescription;
        this.mSteps = new ArrayList<>();
        for (Step step : otherCare.mSteps) {
            Step newStep = new Step();
            newStep.mArriveBy = step.mArriveBy;
            newStep.mCenterID = step.mCenterID;
            newStep.mStepStatus = step.mStepStatus;
            newStep.mStepType = step.mStepType;
            this.mSteps.add(newStep);
        }
    }

    public void shallowCopy(Care otherCare) {
        this.mCareID = otherCare.mCareID;
        this.mCareStatus = otherCare.mCareStatus;
        this.mCareTitle = otherCare.mCareTitle;
        this.mCoordinatorID = otherCare.mCoordinatorID;
        this.mDate = otherCare.mDate;
        this.mDriverID = otherCare.mDriverID;
        this.mFoodValue = otherCare.mFoodValue;
        this.mFoodDescription = otherCare.mFoodDescription;
        this.mSteps = otherCare.mSteps;
    }

    public void WriteToSnapshot(DatabaseReference careRef) {
        Map<String, Object> changeRequests = new HashMap<>();

        changeRequests.put("care_status", CareStatusToCareStatusString(getCareStatus()));
        changeRequests.put("care_title",getCareID());
        changeRequests.put("coordinator_id", getCoordinatorID());
        changeRequests.put("date",getDate());
        changeRequests.put("driver_id",getDriverID());
        changeRequests.put("food_value", getFoodValue());
        changeRequests.put("food_description", getFoodDescription());

        Integer stepId = 0;
        for (Step s : mSteps) {
            String prefix = "steps/" + stepId.toString() + "/";
            changeRequests.put(prefix + "arrive_by", s.getArriveBy());
            changeRequests.put(prefix + "center_id", s.getCenterID());
            changeRequests.put(prefix +"step_status", StepStatusToStepStatusString(s.getStepStatus()));
            changeRequests.put(prefix +"step_type", StepTypeToStepTypeString(s.getStepType()));

            stepId++;
        }
        // Hack - assumes no more than 100 steps.
        while(stepId<100) {
            changeRequests.remove("steps/" + stepId.toString());
            stepId++;
        }
        careRef.updateChildren(changeRequests); // TODO: add completion handler
    }

    public static Care getNewCare() {
        Care newCare = new Care();

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        newCare.setDate(dateFormat.format(new Date()));

        newCare.setDriverID("");
        newCare.setCareStatus(CareStatus.PENDING);
        newCare.setCareID(-1);
        newCare.setCareTitle("Care Title");
        newCare.setFoodValue(0);
        newCare.setFoodDescription("Food Description");

        ArrayList<Step> stepList = new ArrayList<>();
        Step step1 = new Step();
        step1.setArriveBy("00:00");
        step1.setCenterID(0);
        step1.setStepStatus(StepStatus.PENDING);
        step1.setStepType(StepType.DRIVE_DONATION_CENTER);

        Step step2 = new Step(step1);
        step2.setStepType(StepType.DRIVE_COMMUNITY_CENTER);

        stepList.add(step1);
        stepList.add(step2);

        newCare.setSteps(stepList);
        return newCare;
    }

    public enum CareStatus {
        PENDING,
        ACCEPTED,
        ASSIGNED,
        STARTED,
        FINISHED
    }

    public enum StepStatus {
        PENDING,
        STARTED,
        FINISHED
    }

    public enum StepType {
        DRIVE_DONATION_CENTER,
        DRIVE_COMMUNITY_CENTER
    }

    static public class Step {
        private String mArriveBy = null;
        private Integer mCenterID = null;
        private StepStatus mStepStatus = null;
        private StepType mStepType = null;

        public Step(){}
        public Step(Step aStep) {
            mArriveBy = aStep.mArriveBy;
            mCenterID = aStep.mCenterID;
            mStepType = aStep.mStepType;
            mStepStatus = aStep.mStepStatus;
        }

        public String getArriveBy() {
            return mArriveBy;
        }

        public void setArriveBy(String arriveBy) {
            mArriveBy = arriveBy;
        }

        public Integer getCenterID() {
            return mCenterID;
        }

        public void setCenterID(Integer centerID) {
            mCenterID = centerID;
        }

        public StepStatus getStepStatus() {
            return mStepStatus;
        }

        public void setStepStatus(StepStatus stepStatus) {
            mStepStatus = stepStatus;
        }

        public StepType getStepType() {
            return mStepType;
        }

        public void setStepType(StepType stepType) {
            mStepType = stepType;
        }

        /*public void WriteToSnapShot(Firebase stepRef) {

            stepRef.child("arrive_by").setValue(getArriveBy());
            stepRef.child("center_id").setValue(getCenterID());
            stepRef.child("step_status").setValue(StepStatusToStepStatusString(getStepStatus()));
            stepRef.child("step_type").setValue(StepTypeToStepTypeString(getStepType()));

        }*/
    }
}