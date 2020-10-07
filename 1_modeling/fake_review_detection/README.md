# DS-1003-Part-Time-Bandits
Final Project for Machine Learning

All data files can be found here:<br/>
[Shared Google Drive](https://drive.google.com/open?id=13tMrg0ahN-JjCLgwm-hRKZfy_tKtD2AV)

Developed by Tim Connor, Sammie Kim, Antonio Robayo, and Alex Spence (NYU MS Data Science Students)

## Notebook Descriptions
- **Review Processing:** Preprocessing pipeline specifically for the review text. Includes tokenization, lemmatization, as well as the removal of infrequent words and stop words.
- **Additional Features:** Preprocessing pipeline that leverages user and product information. Also includes some text-based summary statistics (review word count & % of english words).
- **EDA:** Scratchpad for attempting to understand the training set. Evaluates distributions of labels, users, products as well as more in-depth analysis of the review text (distribution of languages, words, etc.). THIS NOTEBOOK IS VERY DISORGANIZED

## How to Use the Data Files

  import pandas as pd<br/>
  from scipy import sparse

  df_trn = pd.read_csv("df_train.csv", header=0, index_col=0)<br/>
  Y_trn = df_trn['label']
  
  X_trn = sparse.load_npz('wordcount_train.npz')<br/>
  X_trn = sparse.hstack((X_trn, df_trn.drop(columns=['label']).values))
  
***Replace train with val for validation files***

## Pre-Processed File Details
Pre-processing resulted in the following files:

- **df_train.csv & df_val.csv**: This DOES NOT include the word tokenization, but includes the labels as well as some meta data for the user and product which are being reviewed, specifically:
  - **ex_id:** example id. Unique identifier for the review
  - **user_n_reviews:** Number of reviews in the database for the user making the review
  - **user_avg_rating:** Average ratings for all reviews made by the user making the review
  - **user_review_intensity:** Number of reviews / # of days active (days active = 1 + last review date - first review date)
  - **prod_n_reviews:** Number of reviews in the database for the product being reviewed
  - **prod_avg_rating:** Average ratings for all reviews on the product being reviewed
  - **prod_review_intensity:** Number of reviews / # of days active (days active = 1 + last review date - first review date)
  - **word_count:** Number of words in the review
  - **pct_en:** Percent of words in the review that are recognized by an english spell-checker
  - **label:** 1 for Fake, 0 for Real

- **wordcount_train.csv & wordcount_val.csv:** scipy_sparse_matrices which include the tokenized word counts from sklearn CountVectorizer
  - rows of the matrix = examples, and are aligned with df_train & df_val, respectively.

- **tfidfnorm_train.csv & tfidfnorm_val.csv:** scipy_sparse_matrices which include tf-idf from sklearn's TfidfVectorizer
  - Same layout as previous
  - Tfidf w/ sklearn default normalization (l2)
  - tfidfraw_train.csv & tfidfraw_val.csv have the same layout, but do not normalize the tfidf in case we want to experiment further    with additional pre-processing for any specific algorithms

- **tfidfnorm_trim_train.csv & tfidfnorm_trim_val.csv:** scipy_sparse_matrices which include tf-idf from sklearn's TfidfVectorizer
  - Same layout and tfidf normalization as previous
  - includes additional pre-processing:
    - Remove words that appear 2 or fewer times in training set
    - Remove English "Stop Words" (i.e. common words)
  - tfidfraw_trim_train.csv & tfidfraw_trim_val.csv have the same layout, but do not normalize the tfidf in case we want to experiment further with additional pre-processing for any specific algorithms

+ **vocab.csv & vocab_trim:** These files contain a mapping from column index to feature name (i.e. word) for the wordcount and tfidf files so we can analyze coefficients / feature importance.  Import as a dictionary, or a pd.Series and set the first column as an index. 

***Note: All review data is lemmatized using the WNLemmatizer***
## Class Imbalance & Upsampling
A few good articles on class imbalances the topic (Thanks Sammie for the MLM ones!):

+ https://machinelearningmastery.com/tactics-to-combat-imbalanced-classes-in-your-machine-learning-dataset/
+ https://machinelearningmastery.com/what-is-imbalanced-classification/
+ https://towardsdatascience.com/handling-imbalanced-datasets-in-machine-learning-7a0e84220f28
+ https://www.analyticsvidhya.com/blog/2017/03/imbalanced-data-classification/

**Key points:** 
+ Accuracy is the metric where class imbalance has the largest impact
+ Generally don't want to up-sample validation set.
+ Upsampling shouldn't have a large impact on AUC. This makes sense w/ the theory, since AUC evaluates your ROC at all cut-off points (and upsampling mainly shifts your probability predictions (i.e. our probability of being fake will be higher on aggregate after upsampling), rather than changing the rank order of predictions)
+ Synthetic sampling techniques (like SMOTE) are not practical to NLP problems due to large number of features. 

**Up-Sampled Data Files:**
These files were generated after upsampling the dataset to class parity (i.e. 50% fake, 50% Real). In general, ***the upsampled data files have the same name as the regular files with \_UP appended to them.*** There are a few exceptions, where we did not need to regenerate the data after up sampling. The following should be leveraged during regular and up-sample testing:
+ vocab.csv (used w/ all non-trimmed datasets)
+ df_valid.csv (used w/ all data sets)

## Additional Pre-Processing Attempts:
We evaluated pre-processing pipelines by looking at AUC and Average Precision of a Naive Bayes Classifiier. Based-on the below observations we attempted the following, but did not see improved performance on the validation set.

- Only include recognized english words
- Stem rather than lemmatize
  - Both Porter stemmer and SnowBall stemmer

## Insights gleaned from Exploratory Data Analysis
- ~95% of reviews (and words) are in english
  - However, much of the corpus is not in english (slang, mispellings, etc.).
- 35% of words in the validation set are only seen 1 time
- In nearly all cases, if 1 review by a user is tagged as fake, then all other reviews by that user are also flagged as fake.
  - Said differently, this problem could be reformulated as a fake reviewer identification problem.


