package ext

import (
	"encoding/json"
	"io/ioutil"
	"net/http"

	"github.com/Movie/functionalTest/util"
	"github.com/google/uuid"
)

type TestContentUploadResource struct {
	Token string
}

type TestContentUploadRequestDTO struct {
	Name         string  `json:"name"`
	YearProduced int     `json:"yearProduced"`
	Rating       float32 `json:"rating"`
	Genre        string  `json:"genre"`
	ContentType  string  `json:"contentType"`
	AgeRating    string  `json:"ageRating"`
	IsShareable  bool    `json:"isShareable"`
}

type TestByteStreamUploadDTO struct {
	MovieId    uuid.UUID `json:"movieId"`
	StreamName string    `json:"streamName"`
	ByteStream []byte    `json:"byteStream"`
}

type TestContentUploadResponseDTO struct {
	MovieId uuid.UUID `json:"movieId"`
}

type TestByteStreamUploadResponseDTO struct {
	MovieId    uuid.UUID `json:"movieId"`
	StreamId   uuid.UUID `json:"streamId"`
	Sequence   int       `json:"sequence"`
	Size       int       `json:"size"`
	StreamName string    `json:"streamName"`
}

func (uploadRes TestContentUploadResource) UploadMovie(urlResolver util.URLResolver,
	headerFilter func(map[string]string) map[string]string) (*http.Response, error) {
	fullUrl := urlResolver("/api/user/movie")

	content := TestContentUploadRequestDTO{
		Name:         "First Blood",
		YearProduced: 2000,
		Rating:       7.8,
		Genre:        "ACTION",
		ContentType:  "MOVIE",
		AgeRating:    "12A",
		IsShareable:  true,
	}
	u, err := json.Marshal(content)

	if err != nil {
		panic(err)
	}

	bodyAsString := string(u)
	headers := make(map[string]string)
	headers["Authorization"] = "Bearer " + uploadRes.Token
	headers["Content-Type"] = "application/vnd.movie.add.v1+json"

	if headerFilter != nil {
		headers = headerFilter(headers)
	}

	var h = &WebClient{}
	return h.executePostWithHeaders(fullUrl, bodyAsString, headers)
}

func (uploadRes TestContentUploadResource) UploadVideoForMovie(
	movieId uuid.UUID,
	videoFile string,
	videoBytes []byte,
	urlResolver util.URLResolver) (*http.Response, error) {
	fullUrl := urlResolver("/api/user/movie/stream")

	byteStreamDto := TestByteStreamUploadDTO{
		MovieId:    movieId,
		StreamName: videoFile,
		ByteStream: videoBytes,
	}
	u, err := json.Marshal(byteStreamDto)

	if err != nil {
		panic(err)
	}

	bodyAsString := string(u)
	headers := make(map[string]string)

	headers["Authorization"] = "Bearer " + uploadRes.Token
	headers["Content-Type"] = "application/vnd.movie.stream.add.v1+json"
	headers["Accept"] = "application/vnd.movie.stream.add.v1+json"

	var h = &WebClient{}
	return h.executePostWithHeaders(fullUrl, bodyAsString, headers)
}

func ToUploadResponseDTO(resp *http.Response) *TestContentUploadResponseDTO {
	defer resp.Body.Close()

	bytes, err := ioutil.ReadAll(resp.Body)
	util.PanicOnError(err)
	contentUploadResponseDto := &TestContentUploadResponseDTO{}
	json.Unmarshal(bytes, contentUploadResponseDto)
	return contentUploadResponseDto
}

func ToVideoUploadResponseDTO(resp *http.Response) *TestByteStreamUploadResponseDTO {
	defer resp.Body.Close()

	bytes, err := ioutil.ReadAll(resp.Body)
	util.PanicOnError(err)
	byteStreamUploadResponseDto := &TestByteStreamUploadResponseDTO{}
	json.Unmarshal(bytes, byteStreamUploadResponseDto)
	return byteStreamUploadResponseDto
}
