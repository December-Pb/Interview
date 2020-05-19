#### Leetcode 609

Given a list of directory info including directory path, and all the files with contents in this directory, you need to find out all the groups of duplicate files in the file system in terms of their paths.

A group of duplicate files consists of at least **two** files that have exactly the same content.

A single directory info string in the **input** list has the following format:

`"root/d1/d2/.../dm f1.txt(f1_content) f2.txt(f2_content) ... fn.txt(fn_content)"`

It means there are **n** files `(f1.txt, f2.txt ... fn.txt` with content `f1_content, f2_content ... fn_content`, respectively) in directory `root/d1/d2/.../dm`. Note that n >= 1 and m >= 0. If m = 0, it means the directory is just the root directory.

The **output** is a list of group of duplicate file paths. For each group, it contains all the file paths of the files that have the same content. A file path is a string that has the following format:

`"directory_path/file_name.txt"`

**Example 1:**

```
Input:
["root/a 1.txt(abcd) 2.txt(efgh)", "root/c 3.txt(abcd)", "root/c/d 4.txt(efgh)", "root 4.txt(efgh)"]
Output:  
[["root/a/2.txt","root/c/d/4.txt","root/4.txt"],["root/a/1.txt","root/c/3.txt"]]
```

**Note:**
1. No order is required for the final output.
2. You may assume the directory name, file name and file content only has letters and digits, and the length of file content is in the range of [1,50].
3. The number of files given is in the range of [1,20000].
4. You may assume no files or directories share the same name in the same directory.
5. You may assume each given directory info represents a unique directory. Directory path and file info are separated by a single blank space.

**Follow-up beyond contest:**

1. Imagine you are given a real file system, how will you search files? DFS or BFS?
2. If the file content is very large (GB level), how will you modify your solution?
3. If you can only read the file by 1kb each time, how will you modify your solution?
4. What is the time complexity of your modified solution? What is the most time-consuming part and memory consuming part of it? How to optimize?
5. How to make sure the duplicated files you find are not false positive?

Solution:  
HashMap

```java 
    public List<List<String>> findDuplicate(String[] paths) {
        Map<String, List<String>> hashMap = new HashMap<>();
        for(String str : paths) {
            String[] strList = str.split(" ");
            for(int i = 1; i < strList.length; i++) {
                String[] strSplit = strList[i].split("\\.");
                List<String> resultList;
                if(hashMap.containsKey(strSplit[1])) {
                    resultList = hashMap.get(strSplit[1]);
                }
                else {
                    resultList = new ArrayList<>();
                }
                resultList.add(strList[0] + "/" + strSplit[0] + ".txt");
                hashMap.put(strSplit[1], resultList);
            }
        }
        List <List<String>> res = new ArrayList <> ();
        for (String key: hashMap.keySet()) {
            if (hashMap.get(key).size() > 1)
                res.add(hashMap.get(key));
        }
        return res;
    }
```

**Follow-up Answer:**

**Question-1:**  
Imagine you are given a real file system, how will you search files? DFS or BFS?

**Answer:**  
core idea: DFS
Reason: if depth of directory is not too deeper, which is suitable to use DFS, comparing with BFS.一般来说BFS要比DFS花费更多的内存，所以如果文件的递归层数不深的话可以优先采用BFS，下面的代码首先用md5码处理文件，并将这个md5码代替文件存入内存（节省内存空间），由于是真实的文件系统，所以需要递归的得到每个文件夹中的文件以及他们的子文件夹

```java
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

import java.security.MessageDigest;
import java.math.BigInteger;

class Directory {
	List<Directory> subDirectories;
	List<File> files;
}

public static String makeHashQuick(File file) {
	try {
		FileInputStream fileInput = new FileInputStream(file);
		byte[] data = new byte[(int) file.length()];
		fileInput.read(data);
		fileInput.close();

		MessageDigest md = MessageDigest.getInstance("MD5");
		String fileHash = new BigInteger(1, md.digest(data)).toString(16);
		return fileHash;
	} catch (IOException e) {
		throw new RuntimeException("can't read file: " + file.getAbsolutePath(), e);
	}
}

public static void findDuplicatedFilesByMD5FromFile(Map<String, List<String>> lists, File file) {
	String fileHash = makeHashQuick(file)
	List<String> list = lists.get(fileHash);
	if (list==null) {
		list = new LinkedList<String>();
		lists.put(fileHash, list);
	}
	list.add(file.getAbsolutePath());
}

public static void findDuplicatedFilesByMD5(Map<String, List<String>> lists, Directory dir) {
	for (File file: dir.files) {
		findDuplicatedFilesByMD5FromFile(lists, file);
	}
	for (Directory curDir: dir.subDirectories) {
		findDuplicatedFilesByMD5(lists, curDir);
	}
}

public static List<List<String>> storeDuplicateFiles(Directory dir) {
	List<List<String>> ret = new ArrayList<List<String>>();
	Map<String, List<String>> listsByMD5 = new HashMap<String, List<String>>();
	findDuplicatedFilesByMD5(listsByMD5, dir);
	for (List<String> list: listsByMD5) {
		if (list.size()>1) {
			ret.add(list);
		}
	}
	return ret;
}
```

**Question-2:**  
If the file content is very large (GB level), how will you modify your solution?

**Answer:**  
1. core idea: make use of meta data, like file size before really reading large content.  
    Two steps:
    - DFS to map each size to a set of paths that have that size: Map<Integer, Set>
    - For each size, if there are more than 2 files there, compute hashCode of every file by MD5, if any files with the same size have the same hash, then they are identical files: Map<String, Set>, mapping each hash to the Set of filepaths+filenames. This hash id's are very very big, so we use the Java library BigInteger.

```java
public static void findDuplicatedFilesBySizeFromFile(Map<Integer, List<String>> lists, File file) {
	try {
		Path filePath = Paths.get(file.getAbsolutePath());
		BasicFileAttributes attr = Files.readAttributes(filePath, BasicFileAttributes.class);
		int size = attr.size();
		List<String> list = lists.get(size);
		if (list==null) {
			list = new LinkedList<String>();
			lists.put(size, list);
		}
		list.add(file.getAbsolutePath());
	} catch (IOException e) {
		throw new RuntimeException("can't read file attributes: " + file.getAbsolutePath(), e);
	}
}

public static void findDuplicatedFilesBySize(Map<Integer, List<String>> lists, Directory dir) {
	for (File file: dir.files) {
		findDuplicatedFilesBySizeFromFile(lists, file);
	}
	for (Directory curDir: dir.subDirectories) {
		findDuplicatedFilesBySize(lists, curDir);
	}
}

public static List<List<String>> storeDuplicateFiles(Directory dir) {
	List<List<String>> ret = new ArrayList<List<String>>();
	Map<Integer, List<String>> listsBySize = new HashMap<Integer, List<String>>();
	findDuplicatedFilesBySize(listsBySize, dir);
	Map<String, List<String>> listsByMD5 = new HashMap<String, List<String>>)();
	for (List<String> list: listsBySize) {
		if (list.size()>1) {
			for (String fileName: list) {
				findDuplicatedFilesByMD5FromFile(listsByMD5, new File(fileName));
			}
		}
	}
	for (List<String> list: listsByMD5) {
		if (list.size()>1) {
			ret.add(list);
		}
	}
	return ret;
}
```

2. To optimize Step-2.   
   In GFS, it stores large file in multiple "chunks" (one chunk is 64KB). we have meta data, including the file size, file name and index of different chunks along with each chunk's checkSum(the xor for the content). For step-2, we just compare each file's checkSum.
3. Disadvantage: there might be flase positive duplicates, because two different files might share the same checkSum.

**Question-3:**  
If you can only read the file by 1kb each time, how will you modify your solution?

**Answer:**  
makeHashQuick Function is quick but memory hungry, might likely to run with java -Xmx2G or the likely to increase heap space if RAM avaliable.
we might need to play with the size defined by "buffSize" to make memory efficient.

```java
import java.io.RandomAccessFile;

public static String makeHashLean(File infile) {
	RandomAccessFile file = new RandomAccessFile(infile, "r");
	int buffSize = 1024;
	byte[] buffer = new byte[buffSize];
	// calculate the hash of the whole file
	long offset = file.length();
	long read = 0;
	int unitsize;
	while(read<offset) {
		unitsize = (int) (((offset-read)>=buffSize)?buffSize:(offset-read));
		file.read(buffer, 0, unitsize);
		md.update(buffer, 0, unitsize);
		read += unitsize;
	}
	file.close();
	String hash = new BigInteger(1, md.digest()).toString(16);
	return hash;
}
```

**Question-4:**  
What is the time complexity of your modified solution? What is the most time-consuming part and memory consuming part of it? How to optimize?

**Answer:**  
hashing part is the most time-consuming and memory consuming.
optimize as above mentioned, but also introduce false positive issue.

**Question-5:**  
How to make sure the duplicated files you find are not false positive?

**Answer:**  
Question-2-Answer-1 will avoid it.
We need to compare the content chunk by chunk when we find two "duplicates" using checkSum.