# Loviso Deployment in GCP

## Cloud Architecture
![GLoviso’s Cloud Architecture](loviso-cloud-architecture.jpg)

## Step 1: Activate the API for both Cloud Build and Cloud Run 
```bash
gcloud services enable \
    containerregistry.googleapis.com \
    run.googleapis.com \
```

## Step 2: Clone The Repository then Move to Folder 'Deployment Loviso'
```bash
git clone https://github.com/MFaishalRamadhan/Capstone.git
```
```bash
cd 'Deployment Loviso'
```

## Step 3: Cloud Build and Deploy
```bash
gcloud builds submit --tag gcr.io/[Your_Project_ID]/[Your_Function_Name]
```
```bash
gcloud run deploy --image gcr.io/[Your_Project_ID]/[Your_Function_Name] --platform managed
```
