public class BasicAlgorithm {

    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 3, 4, 6};
        System.out.println(binarySearch(nums, 2));
    }

    public static int binarySearch(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while(left <= right) {
            int mid = left + (right - left) / 2;
            if(nums[mid] == target) {
                return mid;
            }
            if(nums[mid] > target) {
                right = mid - 1;
            }
            if(nums[mid] < target) {
                left = mid + 1;
            }
        }
        return -1;
    }
}